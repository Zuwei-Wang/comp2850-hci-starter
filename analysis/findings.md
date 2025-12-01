# Week 10 Lab 1: Usability Findings & Prioritization

**Date**: 2025-12-01  
**Analysis Based On**: Mock pilot data (5 participants, 4 tasks)  
**Methodology**: Impact + Inclusion - Effort framework

---

## Executive Summary

Pilot testing with 5 participants (4 JS-on, 1 no-JS) revealed **2 critical usability issues**:

1. **T2_edit (Edit Task)**: 57% overall completion rate, 0% no-JS completion rate
2. **T2_edit validation errors**: 33% error rate, with evidence of missing accessible error feedback

**Recommendation**: Prioritize fixes for T2_edit before Week 10 Lab 2 verification testing.

---

## Quantitative Findings

### Task Performance Summary

| Task | n | Completion Rate | Median Time | Error Rate | Status |
|------|---|----------------|-------------|------------|--------|
| **T1_filter** | 5/5 | 100% | 1899ms | 0% | ✅ Good |
| **T2_edit** | 4/7 | **57%** | 1450ms | **33%** | ⚠️ Critical |
| **T3_add** | 5/6 | 83% | 623ms | 17% | ⚠️ Moderate |
| **T4_delete** | 5/5 | 100% | 206ms | 0% | ✅ Good |

### Key Metrics Interpretation

#### T2_edit: Critical Issues
- **Completion rate: 57%** (4/7 attempts succeeded)
  - Industry baseline: ≥90% for core tasks
  - Gap: -33 percentage points → **HIGH IMPACT**
- **Error rate: 33%** (2 validation errors in 6 attempts)
  - 2 participants submitted blank titles before correcting
  - Suggests **missing affordances** or **unclear constraints**
- **No-JS parity failure**:
  - JS-on: 67% completion (4/6 attempts)
  - No-JS: 0% completion (0/1 attempts, 1 failure)
  - **WCAG 2.1.1 violation**: Functionality not available without JavaScript

#### T3_add: Moderate Issues
- **Completion rate: 83%** (5/6 attempts)
  - Below 90% threshold but not critical
  - 1 participant triggered validation error → **17% error rate**
- **Root cause hypothesis**: Form affordances unclear (e.g., required indicator missing)

#### T1_filter & T4_delete: Good Performance
- **100% completion rates** for both tasks
- **0% error rates**
- **Low variability** (MAD < 10ms for T4_delete)
- **Conclusion**: These interactions are working well

---

## Qualitative Findings (Cross-Referenced with Pilot Notes)

### T2_edit Issues

**From pilot-notes.md observations** (mock quotes based on typical user testing):

> **P1** (02:15): "Wait, do I press Enter or click Save?" (hesitated for 5s)

> **P3** (no-JS, 05:30): "I typed the new text and clicked Save, but it went back to the list without changing... did it work?" (Failed - PRG redirect without focus management)

> **P4** (02:48): "Oops, I accidentally cleared it" (Triggered blank_title error, then corrected)

**Interpretation**:
1. **Dual interaction pattern** (Enter vs Save button) causes uncertainty
2. **No-JS path**: PRG redirect loses context, participant couldn't verify success/failure
3. **Error recovery**: Validation errors were correctable but slowed participants down

### T3_add Issues

> **P5** (01:12): "I wasn't sure if there was a character limit" (Typed 80-char title, worked fine but showed uncertainty)

**Interpretation**: Missing constraint information (though no length validation implemented yet)

---

## Issue Prioritization

Using **Impact + Inclusion - Effort** framework (scale 1-5):

### Priority 1 (Score 8-10): Fix Immediately

#### Issue #1: T2_edit No-JS Path Failure
- **Impact**: 5 (blocks task completion for no-JS users)
- **Inclusion**: 5 (disproportionately affects:
  - Users with cognitive disabilities using reader mode
  - Users on assistive technology that strips JS
  - WCAG 2.1.1 Keyboard violation if JS fails)
- **Effort**: 2 (moderate - need focus management + status message)
- **Score**: (5+5) - 2 = **8**

**Evidence**:
- `analysis.csv`: T2_edit, js_mode=off, completion_rate=0.00
- `metrics-mock.csv`: Row 12 shows `T2_edit,fail,5432ms,303,off`
- Pilot notes: P3 couldn't determine if edit succeeded after PRG redirect

**Proposed Fix**:
1. Add `<input type="hidden" name="redirect_focus" value="task-{id}">` to edit form
2. In POST /tasks/{id} route (no-JS path):
   ```kotlin
   // After successful update
   call.response.cookies.append(Cookie("edit_success", taskId))
   call.respondRedirect("/tasks")
   ```
3. In GET /tasks template:
   ```html
   {% if edit_success_cookie %}
   <div role="status" aria-live="polite" tabindex="-1" autofocus>
     Task "{{task.title}}" updated successfully.
   </div>
   {% endif %}
   ```
4. Auto-focus updated task row in list

**Verification**: Retest T2 with no-JS, measure completion rate ≥90%

---

#### Issue #2: T2_edit Validation Errors Not Announced
- **Impact**: 4 (slows down 33% of attempts, causes confusion)
- **Inclusion**: 5 (screen reader users don't hear error announcement)
- **Effort**: 1 (quick fix - add `role=alert` to existing error div)
- **Score**: (4+5) - 1 = **8**

**Evidence**:
- `analysis.csv`: T2_edit, error_rate=0.33
- `metrics-mock.csv`: Rows 3-4 show validation_error → success pattern (P1 corrected)
- Pilot notes: P4 said "Oops, I accidentally cleared it" (saw visual error but SR users might not)

**Proposed Fix**:
1. Add `role="alert"` to error summary div in edit form template:
   ```html
   <div class="error-summary" role="alert" aria-live="assertive">
     <p>Title cannot be blank</p>
   </div>
   ```
2. Ensure error div is present in DOM from start (hidden by CSS), then shown on error
   - Avoids SR announcement issues with late DOM insertion

**Verification**: Test with NVDA, confirm error is announced when triggered

---

### Priority 2 (Score 5-7): Fix If Time Permits

#### Issue #3: T3_add Missing Required Indicator
- **Impact**: 2 (17% error rate, but participants recovered quickly)
- **Inclusion**: 3 (affects SR users who can't see visual form layout)
- **Effort**: 1 (add `aria-required="true"` and `*` to label)
- **Score**: (2+3) - 1 = **4**

**Evidence**:
- `analysis.csv`: T3_add, error_rate=0.17
- Pilot notes: P5 uncertain about constraints

**Proposed Fix**:
```html
<label for="title">
  Task Title <abbr title="required" aria-label="required">*</abbr>
</label>
<input id="title" name="title" aria-required="true" required>
```

**Defer Rationale**: Low impact (83% completion still acceptable), not blocking. Can address in post-evaluation polish.

---

#### Issue #4: T2_edit Dual Interaction Confusion
- **Impact**: 2 (caused 5s hesitation for P1, didn't block completion)
- **Inclusion**: 2 (affects everyone equally, not an accessibility barrier)
- **Effort**: 3 (need to choose: remove Save button OR disable Enter, test both variants)
- **Score**: (2+2) - 3 = **1**

**Evidence**:
- Pilot notes: P1 asked "Enter or Save?"

**Proposed Fix** (if pursued):
- **Option A**: Remove Save button, rely on Enter + "Press Enter to save" hint
- **Option B**: Disable Enter key, force explicit Save click
- Recommend Option A (aligns with inline edit pattern best practices)

**Defer Rationale**: Very low score, not critical. Mention in "Future Improvements" instead.

---

### Priority 3 (Score <5): Document as Known Issue

#### Issue #5: No-JS Performance Gap
- **Impact**: 1 (tasks still completable, just slower)
- **Inclusion**: 1 (no accessibility barrier, expected behavior)
- **Effort**: 5 (fundamental server-side architecture, can't optimize without major refactor)
- **Score**: (1+1) - 5 = **-3**

**Evidence**:
- `analysis.csv`: T3_add median: 3456ms (no-JS) vs 595ms (JS-on)
- Expected due to full page reload vs partial HTMX swap

**Recommendation**: Document in README.md as design trade-off. No action needed.

---

## Inclusion Impact Analysis

### Who Is Most Affected?

**High Impact Groups**:
1. **Screen reader users** (Issue #2: validation errors not announced)
2. **No-JS users** (Issue #1: T2_edit fails completely)
3. **Cognitive disability users** (Issue #1: confusion from missing status feedback)

**Moderate Impact Groups**:
4. **Keyboard-only users** (Issues #2 & #3: lack of aria-required and error announcements slows navigation)

**Low Impact Groups**:
5. **Low vision users** (no reported issues - magnification worked fine)
6. **Motor impairment users** (large click targets, no fine motor control required)

### WCAG 2.2 AA Violations

| Issue | WCAG Criterion | Level | Severity |
|-------|----------------|-------|----------|
| T2_edit no-JS failure | 2.1.1 Keyboard | A | **Critical** |
| Validation errors not announced | 3.3.1 Error Identification | A | **Critical** |
| Missing required indicator | 3.3.2 Labels or Instructions | A | High |

**Conclusion**: Must fix Issues #1 and #2 to achieve WCAG 2.2 AA compliance.

---

## Redesign Brief: Week 10 Lab 2 Scope

### Objectives

1. **Achieve ≥90% completion rate** for T2_edit (both JS-on and no-JS)
2. **Eliminate no-JS parity gap** (currently 67% JS-on vs 0% no-JS)
3. **Ensure WCAG 2.2 AA compliance** for form validation and keyboard access

### In Scope for Lab 2

- [ ] **Fix #1**: Implement PRG with focus management for no-JS edit
  - Add redirect_focus parameter
  - Add success cookie
  - Add auto-focus status message
  - Test with JS disabled
- [ ] **Fix #2**: Add `role="alert"` to validation error div
  - Update edit template
  - Ensure error div present from page load
  - Test with NVDA/JAWS

### Out of Scope (Defer)

- Issue #3 (T3_add required indicator): Low priority, not blocking
- Issue #4 (dual interaction confusion): Very low score, cosmetic
- Issue #5 (no-JS performance): Design trade-off, document instead

### Success Criteria (Verification Testing)

**Quantitative**:
- T2_edit completion rate (JS-on): ≥90% (was 67%)
- T2_edit completion rate (no-JS): ≥90% (was 0%)
- T2_edit error rate: <20% (was 33%)

**Qualitative**:
- Screen reader announces validation errors immediately
- No-JS users can verify edit success without confusion
- Participants express confidence (confidence rating ≥4/5)

**Accessibility**:
- NVDA test: Error announcement confirmed
- No-JS test: Task completable without JavaScript
- Keyboard test: Focus management works correctly

---

## Evidence Chain

```
[Raw Data: metrics-mock.csv]
    ↓ analyze-simple.py
[Analysis: analysis.csv]
    ↓ interpret metrics
[Findings: T2 completion 57%, no-JS 0%]
    ↓ prioritize
[Issues: #1 (score 8), #2 (score 8)]
    ↓ plan fixes
[Redesign: PRG focus mgmt + role=alert]
    ↓ implement (Week 10 Lab 2)
[Verification: retest T2, measure ≥90%]
    ↓ document
[Gradescope Task 2: before/after metrics + fixes]
```

---

## Next Steps

1. **Review this prioritization** with module lead (if available)
2. **Implement Priority 1 fixes** (Issues #1 & #2) in Week 10 Lab 2
3. **Prepare verification test**:
   - Recruit 2-3 new participants (or re-test with original pilots)
   - Test ONLY T2_edit (focus on fixed interaction)
   - Collect after-metrics using same methodology
4. **Document evidence chain** for Gradescope Task 2

---

**Analyst**: (Your name)  
**Date Completed**: 2025-12-01  
**Version**: 1.0
