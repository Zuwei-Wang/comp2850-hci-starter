#!/usr/bin/env python3
"""
Week 10 Lab 1: Analysis Script (Pure Python - No Dependencies)
Calculates summary statistics from pilot metrics data
"""

import csv
from pathlib import Path
from collections import defaultdict
import statistics

# Configuration
INPUT_FILE = 'data/metrics-mock.csv'  # Change to 'data/metrics.csv' for real data
OUTPUT_FILE = 'analysis/analysis.csv'

def calculate_mad(values):
    """Calculate Median Absolute Deviation"""
    if not values:
        return 0
    median = statistics.median(values)
    deviations = [abs(v - median) for v in values]
    return statistics.median(deviations)

def main():
    print("📊 Week 10 Lab 1: Metrics Analysis")
    print("=" * 50)
    
    # Load data
    print(f"\n1. Loading data from {INPUT_FILE}...")
    with open(INPUT_FILE, 'r', encoding='utf-8') as f:
        reader = csv.DictReader(f)
        events = list(reader)
    
    print(f"   ✓ Loaded {len(events)} events")
    
    # Get unique sessions and tasks
    sessions = set(e['session_id'] for e in events)
    tasks = sorted(set(e['task_code'] for e in events))
    print(f"   ✓ Sessions: {len(sessions)}")
    print(f"   ✓ Tasks: {', '.join(tasks)}")
    
    # Group by task_code + js_mode
    groups = defaultdict(list)
    for event in events:
        key = (event['task_code'], event['js_mode'])
        groups[key].append(event)
    
    # Also group by task_code only (for 'all' js_mode)
    task_groups = defaultdict(list)
    for event in events:
        task_groups[event['task_code']].append(event)
    
    results = []
    
    # Calculate per task + js_mode
    print("\n2. Calculating statistics per task + js_mode...")
    for (task, js_mode), group in sorted(groups.items()):
        success = [e for e in group if e['step'] == 'success']
        errors = [e for e in group if e['step'] == 'validation_error']
        failures = [e for e in group if e['step'] == 'fail']
        
        n_success = len(success)
        n_errors = len(errors)
        n_fail = len(failures)
        n_total = len(group)
        
        # Timing stats (only for successful completions)
        if n_success > 0:
            times = [int(e['ms']) for e in success if e['ms']]
            median_ms = int(statistics.median(times))
            mad_ms = int(calculate_mad(times))
            min_ms = min(times)
            max_ms = max(times)
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
            'median_ms': median_ms if median_ms else '',
            'mad_ms': mad_ms if mad_ms else '',
            'min_ms': min_ms if min_ms else '',
            'max_ms': max_ms if max_ms else '',
            'completion_rate': f"{completion_rate:.2f}" if completion_rate is not None else '',
            'error_rate': f"{error_rate:.2f}"
        })
        
        cr_pct = f"{completion_rate:.0%}" if completion_rate is not None else "N/A"
        print(f"   {task:12} {js_mode:4} → n={n_success}/{n_total}, "
              f"median={median_ms}ms, completion={cr_pct}")
    
    # Calculate aggregate (all js_modes combined)
    print("\n3. Calculating aggregate statistics (all js_modes)...")
    for task, group in sorted(task_groups.items()):
        success = [e for e in group if e['step'] == 'success']
        errors = [e for e in group if e['step'] == 'validation_error']
        failures = [e for e in group if e['step'] == 'fail']
        
        n_success = len(success)
        n_errors = len(errors)
        n_fail = len(failures)
        n_total = len(group)
        
        if n_success > 0:
            times = [int(e['ms']) for e in success if e['ms']]
            median_ms = int(statistics.median(times))
            mad_ms = int(calculate_mad(times))
            min_ms = min(times)
            max_ms = max(times)
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
            'median_ms': median_ms if median_ms else '',
            'mad_ms': mad_ms if mad_ms else '',
            'min_ms': min_ms if min_ms else '',
            'max_ms': max_ms if max_ms else '',
            'completion_rate': f"{completion_rate:.2f}" if completion_rate is not None else '',
            'error_rate': f"{error_rate:.2f}"
        })
        
        cr_pct = f"{completion_rate:.0%}" if completion_rate is not None else "N/A"
        print(f"   {task:12} all  → n={n_success}/{n_total}, "
              f"median={median_ms}ms, completion={cr_pct}")
    
    # Save to CSV
    output_path = Path(OUTPUT_FILE)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    
    with open(output_path, 'w', newline='', encoding='utf-8') as f:
        fieldnames = ['task_code', 'js_mode', 'n_success', 'n_errors', 'n_fail', 'n_total',
                      'median_ms', 'mad_ms', 'min_ms', 'max_ms', 'completion_rate', 'error_rate']
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(results)
    
    print(f"\n4. ✅ Analysis complete! Saved to {OUTPUT_FILE}")
    print("\n" + "=" * 50)
    print("\n📋 Summary by Task:")
    
    # Print summary table
    all_results = [r for r in results if r['js_mode'] == 'all']
    print(f"\n{'Task':<15} {'n':<8} {'Median':<10} {'Completion':<12} {'Error Rate':<12}")
    print("-" * 60)
    for r in all_results:
        median_str = f"{r['median_ms']}ms" if r['median_ms'] else "N/A"
        cr_str = f"{float(r['completion_rate']):.0%}" if r['completion_rate'] else "N/A"
        er_str = f"{float(r['error_rate']):.0%}"
        print(f"{r['task_code']:<15} {r['n_success']}/{r['n_total']:<5} {median_str:<10} {cr_str:<12} {er_str:<12}")
    
    print("\n\n🔍 Key Findings:")
    
    # Flag low completion rates
    low_completion = [r for r in all_results if r['completion_rate'] and float(r['completion_rate']) < 0.9]
    if low_completion:
        print("\n⚠️  Low completion rates (<90%):")
        for r in low_completion:
            cr = float(r['completion_rate'])
            print(f"   • {r['task_code']}: {cr:.0%} ({r['n_success']}/{r['n_total']} succeeded)")
    else:
        print("\n✓ All tasks have ≥90% completion rate")
    
    # Flag high error rates
    high_errors = [r for r in all_results if r['error_rate'] and float(r['error_rate']) > 0.2]
    if high_errors:
        print("\n⚠️  High error rates (>20%):")
        for r in high_errors:
            er = float(r['error_rate'])
            print(f"   • {r['task_code']}: {er:.0%} error rate")
    else:
        print("\n✓ All tasks have <20% error rate")
    
    # Check js_mode parity
    print("\n📊 JS vs No-JS Comparison:")
    for task in tasks:
        task_results = {r['js_mode']: r for r in results if r['task_code'] == task}
        
        if 'on' in task_results and 'off' in task_results:
            cr_on = float(task_results['on']['completion_rate']) if task_results['on']['completion_rate'] else 0
            cr_off = float(task_results['off']['completion_rate']) if task_results['off']['completion_rate'] else 0
            
            if abs(cr_on - cr_off) > 0.2:
                print(f"   ⚠️  {task}: Parity gap (JS: {cr_on:.0%}, No-JS: {cr_off:.0%})")
            else:
                print(f"   ✓ {task}: Parity OK")
        else:
            print(f"   ℹ️  {task}: Limited JS-off data")
    
    print("\n" + "=" * 50)
    print("\n📖 Next Steps:")
    print("   1. Review analysis/analysis.csv")
    print("   2. Cross-reference with pilot-notes.md for qualitative context")
    print("   3. Use findings to prioritize backlog items (Activity C)")
    print("   4. Document interpretation in analysis/findings.md")
    print()

if __name__ == '__main__':
    main()
