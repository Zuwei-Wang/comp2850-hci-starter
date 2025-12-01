# Week 10 Lab 2: Before/After Metrics

**Analysis Date**: 2025-12-01  
**Methodology**: Pilot testing with 5 participants (Week 9 before, Week 10 after)  
**Focus**: Task T2_edit (inline edit functionality)

---

## Executive Summary

**Problem**: Week 9 pilot testing revealed T2_edit had a 57% overall completion rate and 0% no-JS completion rate due to missing status feedback after PRG redirects.

**Intervention**: Implemented two Priority 1 fixes:
1. No-JS success message with focus management (cookie-based status display)
2. Validation error `role="alert"` for screen reader announcements

**Result**: *(To be filled after verification testing)*

---

## Metrics Comparison

### T2_edit: Overall Performance

| Metric | Before (Week 9) | After (Week 10) | Δ | Interpretation |
|--------|----------------|----------------|---|----------------|
| **Completion Rate** | 57% (4/7) | *TBD* | *TBD* | Target: ≥90% |
| **Median Time (all)** | 1450ms | *TBD* | *TBD* | Expected: similar or faster |
| **MAD (variability)** | 160ms | *TBD* | *TBD* | Lower MAD = more consistent |
| **Error Rate** | 33% (2/6) | *TBD* | *TBD* | Target: <20% |

---

### T2_edit: JS-on vs No-JS (Parity Check)

| Metric | JS-on Before | JS-on After | No-JS Before | No-JS After | Target |
|--------|-------------|-------------|--------------|-------------|--------|
| **Completion Rate** | 67% (4/6) | *TBD* | **0%** (0/1) | *TBD* | ≥90% both |
| **Median Time** | 1450ms | *TBD* | N/A (failed) | *TBD* | Both completable |

**Parity Gap (Before)**: 67 percentage points (67% vs 0%)  
**Parity Gap (After)**: *TBD*  
**Target**: <10 percentage points

---

## Detailed Findings (To Be Filled After Verification)

### Before (Week 9 Baseline)

**T2_edit Issues**:
1. **No-JS path**: Participant P3 (no-JS) could not determine if edit succeeded after PRG redirect
   - Result: Marked as `fail` after 5432ms
   - Quote: "I typed the new text and clicked Save, but it went back to the list without changing... did it work?"
2. **Validation errors**: 2 participants (P1, P4) submitted blank titles, triggering errors
   - P1: 890ms validation_error, then 1654ms success (corrected)
   - P4: 1123ms validation_error, then 1589ms success (corrected)
   - Issue: Errors not announced to screen readers (role="alert" missing from no-JS path)

**Raw Data** (from `data/metrics-mock.csv`):
```
2025-12-01T10:18:12Z,a3f87c,req003,T2_edit,validation_error,blank_title,890,400,on
2025-12-01T10:18:45Z,a3f87c,req004,T2_edit,success,,1654,200,on
2025-12-01T11:25:02Z,b7e4f1,req008,T2_edit,success,,1268,200,on
2025-12-01T14:09:30Z,c9d2a8,req012,T2_edit,fail,,5432,303,off  ← No-JS failure
2025-12-01T15:33:42Z,d1f5b9,req017,T2_edit,validation_error,blank_title,1123,400,on
2025-12-01T15:34:38Z,d1f5b9,req018,T2_edit,success,,1589,200,on
2025-12-01T16:15:01Z,e8c3d2,req022,T2_edit,success,,1312,200,on
```

**Analysis**:
- n_success: 4
- n_errors: 2
- n_fail: 1
- n_total: 7
- Completion rate: 4/7 = 57%
- Error rate: 2/(4+2) = 33%

---

### After (Week 10 Verification)

**Changes Implemented**:
1. **No-JS success message**:
   - Set `edit_success` cookie with task ID and title
   - Display success message at top of page after PRG redirect
   - Auto-focus message with `tabindex="-1"` and `autofocus`
   - Message has `role="status"` for screen reader announcement
2. **No-JS validation errors**:
   - Set `edit_error` cookie when blank title submitted
   - Pass error to `_edit.peb` template
   - Error div has `role="alert" aria-live="assertive"`
   - Auto-focus error message after redirect

**Test Protocol**:
- Recruit 2-3 participants (or re-test with original pilots)
- Use same task scenario: "Change 'Draft report' to 'Submit report by Friday'"
- Test both JS-on and no-JS variants
- Record metrics using same methodology

**Predicted Results**:
- No-JS completion rate: **100%** (participants can now see success confirmation)
- JS-on completion rate: **≥90%** (validation errors announced)
- Overall completion rate: **≥90%**
- Error rate: **<20%** (clearer affordances)

*(Actual results to be filled after verification testing)*

---

## Verification Test Results (Template)

### Participant P6 (JS-on)

**Date**: _______  
**Time**: _______  

**T2_edit Attempt**:
- Start time: _______
- Actions:
  - 00:05 - Clicked Edit
  - 00:12 - Input appeared inline
  - 00:25 - Typed new title
  - 00:32 - Pressed Enter
  - 00:35 - Status message appeared
- **Completion**: ☐ Success ☐ Fail ☐ Gave Up
- **Duration**: _____ ms
- **Errors**: _____ (count validation errors)
- **Confidence rating**: _____ / 5

**Observations**:
- _______________________________

---

### Participant P7 (no-JS)

**Date**: _______  
**Time**: _______  

**T2_edit Attempt**:
- Start time: _______
- Actions:
  - 00:08 - Clicked Edit button
  - 00:15 - Page loaded with edit form
  - 00:28 - Typed new title
  - 00:35 - Clicked Save button
  - 00:42 - Page redirected to task list
  - 00:45 - Success message appeared and received focus
- **Completion**: ☐ Success ☐ Fail ☐ Gave Up
- **Duration**: _____ ms
- **Success message noticed?**: ☐ Yes ☐ No
- **Confidence rating**: _____ / 5

**Observations**:
- _______________________________

---

### Aggregate After-Metrics

*(To be calculated after verification testing)*

| Participant | Variant | Completion | Duration | Errors | Confidence |
|-------------|---------|------------|----------|--------|------------|
| P6 | JS-on | Success | _____ ms | _____ | _____ / 5 |
| P7 | no-JS | Success | _____ ms | _____ | _____ / 5 |
| P8 | JS-on | Success | _____ ms | _____ | _____ / 5 |

**Summary Statistics**:
- Completion rate: _____ / _____ = _____%
- Median time: _____ ms
- MAD: _____ ms
- Error rate: _____ / _____ = _____%

---

## Statistical Significance (Optional)

**Sample Size**:
- Before: 7 attempts (5 participants, 2 with errors)
- After: _____ attempts

**Limitations**:
- Small sample size (n < 30) → Results not statistically generalizable
- Formative evaluation → Focus on **practical significance** not p-values
- Nielsen's 5-user rule: 5 participants find ~85% of usability issues

**Interpretation**:
- **Practical significance**: If completion rate improves from 57% to ≥90%, this represents a **meaningful** improvement for real users (33+ percentage point increase)
- **Effect size**: Large effect (Cohen's h ≈ 0.8 for 57% → 90% improvement)
- **Conclusion**: Even without statistical significance testing, a 33+ pp improvement is **practically significant** for inclusive design

---

## Evidence Chain Documentation

```
[Baseline Data: Week 9 metrics-mock.csv]
    ↓ analysis/analyze-simple.py
[Analysis: T2 completion 57%, no-JS 0%]
    ↓ analysis/findings.md (prioritization)
[Priority 1: Issue #1 (score 8), Issue #2 (score 8)]
    ↓ Week 10 Lab 2 implementation
[Code Changes: TaskRoutes.kt + index.peb + _edit.peb]
    ↓ regression-checklist.md
[Verification: Manual testing + metrics collection]
    ↓ this document
[After-Metrics: Completion ≥90%, parity restored]
    ↓ Gradescope Task 2
[Evidence Pack: before/after + diffs + screenshots]
```

---

## Conclusion (To Be Written After Verification)

**Did the fixes work?**
- *(Yes/No, with evidence)*

**Who benefits?**
- *(e.g., No-JS users, SR users, all participants)*

**Remaining issues?**
- *(Any new problems discovered during verification)*

**Next steps?**
- *(e.g., Deploy to production, monitor real-world usage, iterate)*

---

**Analyst**: (Your name)  
**Date**: 2025-12-01  
**Version**: Draft (awaiting verification testing)
