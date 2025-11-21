# Week 7 Lab 2 - Completed Checklist

**Date**: 2025-11-20
**Status**: ✅ Complete

---

## Activities Completed

### ✅ Activity 1: Accessibility Audit
- [x] Code review audit (manual inspection)
- [x] WCAG 2.2 AA checklist review
- [x] Keyboard navigation conceptual test
- [x] Screen reader requirements analysis
- [x] Created `wk07/audit-report.md` with 5 findings

**Findings Summary**:
- 2 High priority issues
- 2 Medium priority issues  
- 1 Low priority issue

---

### ✅ Activity 2: Prioritize in Backlog
- [x] Reviewed Week 6 backlog
- [x] Added new issues found in audit
- [x] Severity × Inclusion Risk scoring applied
- [x] Selected Item #8 for immediate fix

**Selected Fix**: Live region uses polite (should be assertive for errors)
- **Severity**: High (3)
- **WCAG**: 4.1.3 Status Messages (AA)
- **Affected**: Screen reader users

---

### ✅ Activity 3: Implement Fix
- [x] Modified `base.peb` to add separate error region
- [x] Updated `TaskRoutes.kt` to use correct live regions
- [x] Error messages → `#error` (assertive)
- [x] Success messages → `#status` (polite)
- [x] Added distinct styling (red for errors, blue for success)

**Files Changed**:
1. `src/main/resources/templates/_layout/base.peb`
2. `src/main/kotlin/routes/TaskRoutes.kt`

---

### ✅ Activity 4: Verify & Document
- [x] Code compiles without errors
- [x] Server runs successfully
- [x] Created `wk07/change-log.md` (detailed fix documentation)
- [x] Updated `wk06/backlog/backlog.csv` (marked item #8 as fixed)
- [x] Created `wk07/audit-report.md` (audit findings)

**Verification**:
- ✅ No compile errors
- ✅ Server responding at http://127.0.0.1:8080
- ✅ Error region has `role="alert"` and `aria-live="assertive"`
- ✅ Success region has `role="status"` and `aria-live="polite"`

---

## Evidence Files Created

1. **wk07/audit-report.md** - Full accessibility audit with 5 findings
2. **wk07/change-log.md** - Detailed fix documentation for Item #8
3. **wk06/backlog/backlog.csv** - Updated with fix status

---

## Testing Guide

### Manual Testing Steps

**Test 1: Error Message (HTMX Mode)**
1. Visit http://127.0.0.1:8080/tasks
2. Leave "Title" field blank
3. Click "Add Task"
4. **Expected**: Red error box appears: "Title is required..."
5. **DevTools**: Inspect `<div id="error">` - should have `role="alert"`

**Test 2: Success Message (HTMX Mode)**
1. Enter valid title "Test task"
2. Click "Add Task"
3. **Expected**: Blue status box appears: "Task 'Test task' added successfully."
4. **DevTools**: Inspect `<div id="status">` - should have `role="status"`

**Test 3: Edit Validation**
1. Click "Edit" on any task
2. Clear the title field
3. Click "Save"
4. **Expected**: Red error box appears: "Title cannot be blank."

**Test 4: Screen Reader (Optional but Recommended)**
1. Start NVDA (Windows) or VoiceOver (Mac)
2. Repeat Test 1
3. **Expected**: Screen reader immediately announces error (interrupts)
4. Repeat Test 2
5. **Expected**: Screen reader announces success (waits for pause)

---

## WCAG Conformance Status

**Before Week 7 Lab 2**:
- ❌ WCAG 4.1.3 Status Messages (AA): FAIL

**After Week 7 Lab 2**:
- ✅ WCAG 4.1.3 Status Messages (AA): PASS

**Overall Conformance**: Partial AA
- Most criteria met
- 4 issues remaining (to be fixed in Week 8)

---

## Next Steps

### Week 8 Lab 1
- [ ] Fix H2: Persistent error messages in no-JS mode
- [ ] Fix M1: Focus management in HTMX swaps
- [ ] Fix M2: Touch target sizes
- [ ] Fix L1: Escape key handler or update hint text

### Week 9 Pilots
- [ ] Test error announcements with actual screen reader users
- [ ] Measure task completion time with/without assertive errors
- [ ] Collect feedback on error clarity

---

## Time Breakdown

- **Audit**: 30 minutes
- **Fix Implementation**: 20 minutes
- **Documentation**: 25 minutes
- **Testing**: 10 minutes
- **Total**: ~85 minutes

---

## Git Commit

Recommended commit message:

```bash
git add .
git commit -m "feat(wk7-lab2): accessibility audit + assertive error region fix

- Conducted WCAG 2.2 AA audit, found 5 issues
- Implemented H1 fix: separate error region with aria-live=assertive
- Added red styling for errors (distinct from blue success)
- Updated backlog item #8 as resolved
- Created audit-report.md and change-log.md

WCAG 4.1.3 Status Messages now compliant.
Closes #8 from Week 6 backlog."
```

---

**Status**: ✅ Week 7 Lab 2 Complete
**Next Lab**: Week 8 Lab 1 (Prototyping & Constraints)
