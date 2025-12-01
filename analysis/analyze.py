#!/usr/bin/env python3
"""
Week 10 Lab 1: Analysis Script
Calculates summary statistics from pilot metrics data
"""

import pandas as pd
import numpy as np
from pathlib import Path

# Configuration
INPUT_FILE = 'data/metrics-mock.csv'  # Change to 'data/metrics.csv' for real data
OUTPUT_FILE = 'analysis/analysis.csv'

def calculate_mad(values):
    """Calculate Median Absolute Deviation"""
    median = np.median(values)
    deviations = np.abs(values - median)
    return np.median(deviations)

def main():
    print("📊 Week 10 Lab 1: Metrics Analysis")
    print("=" * 50)
    
    # Load data
    print(f"\n1. Loading data from {INPUT_FILE}...")
    df = pd.read_csv(INPUT_FILE)
    print(f"   ✓ Loaded {len(df)} events")
    print(f"   ✓ Sessions: {df['session_id'].nunique()}")
    print(f"   ✓ Tasks: {df['task_code'].unique().tolist()}")
    
    # Filter success rows for timing analysis
    success_df = df[df['step'] == 'success'].copy()
    print(f"\n2. Filtered to {len(success_df)} successful task completions")
    
    # Initialize results list
    results = []
    
    # Calculate per task + js_mode
    print("\n3. Calculating statistics per task + js_mode...")
    for (task, js_mode), group in df.groupby(['task_code', 'js_mode']):
        success = group[group['step'] == 'success']
        validation_errors = group[group['step'] == 'validation_error']
        failures = group[group['step'] == 'fail']
        
        n_success = len(success)
        n_errors = len(validation_errors)
        n_fail = len(failures)
        n_total = len(group)
        
        # Timing stats (only for successful completions)
        if n_success > 0:
            times = success['ms'].values
            median_ms = int(np.median(times))
            mad_ms = int(calculate_mad(times))
            min_ms = int(times.min())
            max_ms = int(times.max())
        else:
            median_ms = mad_ms = min_ms = max_ms = None
        
        # Completion rate
        completion_rate = n_success / n_total if n_total > 0 else None
        
        # Error rate (errors / (errors + successes))
        error_denominator = n_success + n_errors
        error_rate = n_errors / error_denominator if error_denominator > 0 else 0.0
        
        results.append({
            'task_code': task,
            'js_mode': js_mode,
            'n_success': n_success,
            'n_errors': n_errors,
            'n_fail': n_fail,
            'n_total': n_total,
            'median_ms': median_ms,
            'mad_ms': mad_ms,
            'min_ms': min_ms,
            'max_ms': max_ms,
            'completion_rate': round(completion_rate, 2) if completion_rate else None,
            'error_rate': round(error_rate, 2) if error_rate else 0.0
        })
        
        print(f"   {task:12} {js_mode:4} → "
              f"n={n_success}/{n_total}, "
              f"median={median_ms}ms, "
              f"completion={completion_rate:.0%}" if completion_rate else "N/A")
    
    # Calculate aggregate (all js_modes combined)
    print("\n4. Calculating aggregate statistics (all js_modes)...")
    for task, group in df.groupby(['task_code']):
        success = group[group['step'] == 'success']
        validation_errors = group[group['step'] == 'validation_error']
        failures = group[group['step'] == 'fail']
        
        n_success = len(success)
        n_errors = len(validation_errors)
        n_fail = len(failures)
        n_total = len(group)
        
        if n_success > 0:
            times = success['ms'].values
            median_ms = int(np.median(times))
            mad_ms = int(calculate_mad(times))
            min_ms = int(times.min())
            max_ms = int(times.max())
        else:
            median_ms = mad_ms = min_ms = max_ms = None
        
        completion_rate = n_success / n_total if n_total > 0 else None
        error_denominator = n_success + n_errors
        error_rate = n_errors / error_denominator if error_denominator > 0 else 0.0
        
        results.append({
            'task_code': task,
            'js_mode': 'all',
            'n_success': n_success,
            'n_errors': n_errors,
            'n_fail': n_fail,
            'n_total': n_total,
            'median_ms': median_ms,
            'mad_ms': mad_ms,
            'min_ms': min_ms,
            'max_ms': max_ms,
            'completion_rate': round(completion_rate, 2) if completion_rate else None,
            'error_rate': round(error_rate, 2) if error_rate else 0.0
        })
        
        print(f"   {task:12} all  → "
              f"n={n_success}/{n_total}, "
              f"median={median_ms}ms, "
              f"completion={completion_rate:.0%}" if completion_rate else "N/A")
    
    # Convert to DataFrame
    results_df = pd.DataFrame(results)
    
    # Save to CSV
    output_path = Path(OUTPUT_FILE)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    results_df.to_csv(output_path, index=False)
    
    print(f"\n5. ✅ Analysis complete! Saved to {OUTPUT_FILE}")
    print("\n" + "=" * 50)
    print("\n📋 Summary by Task:")
    print(results_df[results_df['js_mode'] == 'all'][
        ['task_code', 'n_success', 'n_total', 'median_ms', 'completion_rate', 'error_rate']
    ].to_string(index=False))
    
    print("\n\n🔍 Key Findings:")
    
    # Flag low completion rates
    low_completion = results_df[
        (results_df['js_mode'] == 'all') & 
        (results_df['completion_rate'] < 0.9)
    ]
    if not low_completion.empty:
        print("\n⚠️  Low completion rates (<90%):")
        for _, row in low_completion.iterrows():
            print(f"   • {row['task_code']}: {row['completion_rate']:.0%} "
                  f"({row['n_success']}/{row['n_total']} succeeded)")
    else:
        print("\n✓ All tasks have ≥90% completion rate")
    
    # Flag high error rates
    high_errors = results_df[
        (results_df['js_mode'] == 'all') & 
        (results_df['error_rate'] > 0.2)
    ]
    if not high_errors.empty:
        print("\n⚠️  High error rates (>20%):")
        for _, row in high_errors.iterrows():
            print(f"   • {row['task_code']}: {row['error_rate']:.0%} error rate")
    else:
        print("\n✓ All tasks have <20% error rate")
    
    # Check js_mode parity
    print("\n📊 JS vs No-JS Comparison:")
    for task in df['task_code'].unique():
        task_data = results_df[results_df['task_code'] == task]
        js_on = task_data[task_data['js_mode'] == 'on']
        js_off = task_data[task_data['js_mode'] == 'off']
        
        if not js_on.empty and not js_off.empty:
            cr_on = js_on.iloc[0]['completion_rate']
            cr_off = js_off.iloc[0]['completion_rate']
            if cr_on and cr_off and abs(cr_on - cr_off) > 0.2:
                print(f"   ⚠️  {task}: Parity gap (JS: {cr_on:.0%}, No-JS: {cr_off:.0%})")
            else:
                print(f"   ✓ {task}: Parity OK")
        elif not js_off.empty:
            print(f"   ℹ️  {task}: Only tested with JS-on")
    
    print("\n" + "=" * 50)
    print("\n📖 Next Steps:")
    print("   1. Review analysis/analysis.csv")
    print("   2. Cross-reference with pilot-notes.md for qualitative context")
    print("   3. Use findings to prioritize backlog items (Activity C)")
    print("   4. Document interpretation in analysis/findings.md")
    print()

if __name__ == '__main__':
    main()
