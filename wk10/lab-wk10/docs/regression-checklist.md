# Week 10 Lab 2: Regression Testing Checklist

**Date**: 2025-12-01  
**Tester**: (Your name)  
**Changes Tested**: 
- Fix #1: No-JS edit success with focus management + status message
- Fix #2: Validation error `role="alert"` for screen readers

---

## Test Environment

- **Browser**: (e.g., Chrome 120, Firefox 121, Safari 17)
- **Operating System**: Windows 11
- **Screen Reader** (if applicable): (e.g., NVDA 2023.3, JAWS 2023)
- **Server URL**: http://127.0.0.1:8080

---

## Priority 1 Fixes Verification

### Fix #1: No-JS Edit Success with Status Message

**Test Steps**:
1. Disable JavaScript in browser (DevTools → Settings → Disable JavaScript)
2. Navigate to http://127.0.0.1:8080/tasks
3. Ensure at least one task exists (add manually if empty)
4. Click "Edit" button next to a task
5. Change the task title to something new (e.g., "Updated task title")
6. Click "Save" button
7. Observe page redirect back to task list

**Expected Results**:
- [ ] Page redirects to `/tasks` after save (PRG pattern)
- [ ] Success message appears at top of page: "✓ Task '[title]' updated successfully."
- [ ] Success message has `role="status"` attribute
- [ ] Success message receives focus automatically (focus indicator visible)
- [ ] Updated task appears in list with new title
- [ ] No confusion about whether edit succeeded

**Actual Results**:
- Status: ☐ Pass ☐ Fail
- Notes: _______________________________

---

### Fix #2: Validation Error Announcement (Screen Reader)

**Test Steps (with NVDA/JAWS)**:
1. Enable screen reader
2. Navigate to http://127.0.0.1:8080/tasks with JavaScript **enabled** (HTMX variant)
3. Click "Edit" on a task
4. Clear the entire title text (make it blank)
5. Press Enter or click "Save"
6. Listen for screen reader announcement

**Expected Results**:
- [ ] Screen reader announces: "Alert: Title is required. Please enter at least one character."
- [ ] Error message has `role="alert"` attribute
- [ ] Error message is in DOM and visible
- [ ] Input field has `aria-invalid="true"`
- [ ] Input field has `aria-describedby` linking to error
- [ ] Participant can correct error and re-submit successfully

**Actual Results**:
- Status: ☐ Pass ☐ Fail
- Screen reader used: _______________________________
- Announcement heard: _______________________________
- Notes: _______________________________

---

**Test Steps (no-JS variant)**:
1. Disable JavaScript
2. Navigate to `/tasks`
3. Click "Edit" on a task
4. Clear title text (make it blank)
5. Click "Save"
6. Observe error message after PRG redirect

**Expected Results**:
- [ ] Page redirects back to edit mode (`?editing={id}`)
- [ ] Error message appears: "Title is required..."
- [ ] Error message has `role="alert"` attribute
- [ ] Error message receives focus automatically
- [ ] Input field still contains blank value (state preserved)
- [ ] Participant can type new text and re-submit

**Actual Results**:
- Status: ☐ Pass ☐ Fail
- Notes: _______________________________

---

## Regression Testing (No Breaking Changes)

### Keyboard Navigation

**Test Steps**:
1. Use only keyboard (no mouse)
2. Navigate through entire page using Tab key
3. Test edit workflow: Tab to Edit → Enter → Tab to input → Type → Tab to Save → Enter

**Expected Results**:
- [ ] All interactive elements reachable via Tab
- [ ] Focus indicators visible on all focusable elements
- [ ] No keyboard traps
- [ ] Logical tab order (add form → filter → task list)
- [ ] Edit/Save/Cancel buttons accessible
- [ ] Success/error messages focusable (if tabindex="-1")

**Actual Results**:
- Status: ☐ Pass ☐ Fail
- Issues found: _______________________________

---

### HTMX Variant (JavaScript Enabled)

**Test Steps**:
1. Enable JavaScript
2. Test inline edit:
   - Click Edit → Input appears inline → Type new text → Press Enter
3. Test delete with confirmation:
   - Click Delete → Modal appears → Click Confirm
4. Test filter (Active Search):
   - Type in filter box → List updates live

**Expected Results**:
- [ ] Inline edit works without full page reload
- [ ] Status message appears after edit: "Task updated successfully."
- [ ] Delete confirmation modal works
- [ ] Filter updates live (Active Search pattern)
- [ ] No JavaScript errors in console

**Actual Results**:
- Status: ☐ Pass ☐ Fail
- Console errors: _______________________________

---

### No-JS Parity

**Test Steps**:
1. Disable JavaScript
2. Test all CRUD operations:
   - Add task → Submit → Task appears
   - Edit task → Save → Task updated
   - Delete task → Submit → Task removed
   - Filter tasks → Submit → List filtered

**Expected Results**:
- [ ] Add task works (POST /tasks → redirect)
- [ ] Edit task works (POST /tasks/{id} → redirect with success message)
- [ ] Delete task works (POST /tasks/{id}/delete → redirect)
- [ ] Filter works (GET /tasks?q=... → filtered list)
- [ ] All functionality available without JavaScript
- [ ] PRG pattern prevents duplicate submissions

**Actual Results**:
- Status: ☐ Pass ☐ Fail
- Broken functionality: _______________________________

---

### Visual Rendering

**Test Steps**:
1. Test at different zoom levels (100%, 150%, 200%)
2. Test in narrow viewport (mobile, 375px width)
3. Check color contrast (error messages, buttons)

**Expected Results**:
- [ ] No horizontal scrolling at 200% zoom
- [ ] Text reflows correctly
- [ ] Success message green background has sufficient contrast
- [ ] Error message red text has sufficient contrast (WCAG AA: ≥4.5:1)
- [ ] Focus indicators visible against all backgrounds

**Actual Results**:
- Status: ☐ Pass ☐ Fail
- Issues: _______________________________

---

## WCAG 2.2 AA Compliance Check

| Criterion | Requirement | Status | Notes |
|-----------|-------------|--------|-------|
| **2.1.1 Keyboard** | All functionality via keyboard | ☐ Pass ☐ Fail | |
| **3.3.1 Error Identification** | Errors identified in text and programmatically | ☐ Pass ☐ Fail | |
| **3.3.2 Labels or Instructions** | Labels provided for inputs | ☐ Pass ☐ Fail | |
| **4.1.3 Status Messages** | Status messages announced | ☐ Pass ☐ Fail | |

---

## Summary

**Total Tests**: 6 major areas  
**Passed**: _____  
**Failed**: _____  
**Blocking Issues**: (List any regressions or new bugs)

**Recommendation**: ☐ Ready for verification testing | ☐ Needs fixes before verification

---

**Tester Signature**: ___________  
**Date Completed**: 2025-12-01  
**Version Tested**: (Git commit hash: _________)
