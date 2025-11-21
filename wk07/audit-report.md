# Week 7 Accessibility Audit Report

**Date**: 2025-11-20
**Auditor**: Week 7 Lab 2
**Scope**: Task Manager (http://localhost:8080/tasks)
**Methods**: Automated (axe DevTools conceptual), Manual (Keyboard + Code Review), WCAG 2.2 AA

---

## Executive Summary

**Total Issues Found**: 5
- **Critical/High**: 2 (must fix)
- **Medium**: 2 (should fix)
- **Low**: 1 (nice to have)

**Conformance Level**: Partial WCAG 2.2 AA compliance
- Several Level A/AA failures identified
- Strong foundation with HTMX + ARIA structure

---

## Findings

### 🔴 HIGH Priority

#### H1: Error Messages Not Announced with Assertive Priority (SR)
**WCAG**: 4.1.3 Status Messages (AA)
**Location**: `base.peb` line 70 - `<div id="status" role="status" aria-live="polite">`
**Issue**: Validation errors use `aria-live="polite"`, which waits for SR to pause before announcing. Errors should use `aria-live="assertive"` to interrupt immediately.

**Impact**: Screen reader users don't realize form submission failed until they navigate away.

**Affected Groups**: Screen reader (SR)
**Severity**: High (3)
**Evidence**: Code review + WCAG 4.1.3 requirement

**Fix**: 
- Keep `aria-live="polite"` for success messages
- Add separate error region with `aria-live="assertive"`
- Or dynamically change `aria-live` based on message type

**Backlog Ref**: Item #8

---

#### H2: No Persistent Error Messages in No-JS Mode
**WCAG**: 3.3.1 Error Identification (A), 3.3.3 Error Suggestion (AA)
**Location**: `TaskRoutes.kt` - POST /tasks, POST /tasks/{id} (no-JS path)
**Issue**: When validation fails in no-JS mode, POST redirects to GET without error message. User sees form again but doesn't know what went wrong.

**Impact**: Users must guess why submission failed; may retry same invalid input.

**Affected Groups**: Cognitive, Screen reader, Low digital literacy
**Severity**: High (3)
**Evidence**: Manual testing with JS disabled

**Fix**: 
- Add error query parameter to redirect URL: `/tasks?error=title_required`
- Display error summary at top of page when error param present
- Ensure error summary has `role="alert"`

**Backlog Ref**: Item #6

---

### 🟡 MEDIUM Priority

#### M1: Focus Not Set on Edit Input (HTMX Mode)
**WCAG**: 2.4.3 Focus Order (A)
**Location**: `_edit.peb` - `autofocus` attribute present but may not work consistently in HTMX swaps
**Issue**: When Edit button clicked, focus should move to title input. `autofocus` works on initial page load but not on HTMX fragment swaps in some browsers.

**Impact**: Keyboard users must Tab to find the input after clicking Edit.

**Affected Groups**: Keyboard, Motor, Screen reader
**Severity**: Medium (2)
**Evidence**: HTMX documentation (autofocus in swapped content is unreliable)

**Fix**: 
- Add `hx-on::after-swap="this.querySelector('input').focus()"` to Edit button
- Ensures focus moves programmatically after swap completes

**Backlog Ref**: New issue (found in audit)

---

#### M2: Edit/Delete Buttons Visually Cramped
**WCAG**: 2.5.5 Target Size (AA, Level AAA is 44×44px)
**Location**: `_item.peb` - buttons in inline form
**Issue**: Edit and Delete buttons are small (Pico.css default ~36×36px on mobile). May be hard to tap on touchscreens.

**Impact**: Mobile users with motor impairments may accidentally tap wrong button.

**Affected Groups**: Motor, Touchscreen users
**Severity**: Medium (2)
**Evidence**: Visual inspection + WCAG 2.5.5

**Fix**: 
- Add `style="min-width: 44px; min-height: 44px;"` to buttons
- Or add CSS rule for touch targets

**Backlog Ref**: New issue (found in audit)

---

### 🟢 LOW Priority

#### L1: Hint Text Could Be More Descriptive
**WCAG**: 3.3.2 Labels or Instructions (A) - met, but can improve
**Location**: `_edit.peb` - `<small id="hint-{{ task.id }}">Press Enter to save, Escape to cancel.</small>`
**Issue**: Hint mentions Escape key, but Escape doesn't actually work (no JS handler implemented).

**Impact**: Users expect Escape to work; minor UX inconsistency.

**Affected Groups**: Keyboard users
**Severity**: Low (1)
**Evidence**: Code review

**Fix**: 
- Either: Add Escape key handler to cancel edit
- Or: Remove "Escape to cancel" from hint text

**Backlog Ref**: New issue (found in audit)

---

## Checklist Summary

| Category | Status | Notes |
|----------|--------|-------|
| **Keyboard Navigation** | ✅ Mostly Pass | All actions keyboard-accessible; focus order logical |
| **Screen Reader** | ⚠️ Partial | ARIA labels good; live region needs assertive for errors |
| **No-JS Mode** | ⚠️ Partial | Core functionality works; error messages missing |
| **Semantic HTML** | ✅ Pass | H1/H2 structure correct; landmarks present |
| **Forms** | ✅ Pass | Labels bound; aria-describedby used correctly |
| **Contrast** | ✅ Pass | Pico.css defaults meet AA (tested visually) |
| **Dynamic Updates** | ⚠️ Partial | Status messages work; assertive needed for errors |

---

## Recommended Fix Priority

**Week 7 Lab 2 (Complete 1)**:
1. ✅ Fix H1: Error messages with assertive ARIA (implement below)

**Week 8 (Stretch Goals)**:
2. Fix H2: Persistent error messages in no-JS mode
3. Fix M1: Focus management in HTMX swaps
4. Fix M2: Touch target sizes

---

## Evidence Files

- `wk07/audit-report.md` (this file)
- `wk07/change-log.md` (fix documentation)
- Screenshots: (capture before/after with DevTools showing aria-live change)

---

## References

- W3C (2024). WCAG 2.2 Quick Reference. https://www.w3.org/WAI/WCAG22/quickref/
- Deque (2024). "ARIA Live Regions". https://www.deque.com/blog/aria-live-regions-updated/
- GOV.UK (2024). Error Message Pattern. https://design-system.service.gov.uk/components/error-message/
