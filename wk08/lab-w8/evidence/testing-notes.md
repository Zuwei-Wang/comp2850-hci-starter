# Week 8 Lab 2 Testing Notes

**Date**: 2025-11-21
**Server**: http://127.0.0.1:8080

---

## Implementation Status ✅

### Completed Features
1. ✅ Server-side validation for POST /tasks
   - Blank title validation
   - Max 200 characters validation
   - Dual-path error responses (HTMX OOB + no-JS redirect)

2. ✅ Accessible error handling
   - Error summary with `role="alert"` for no-JS
   - Error messages with `aria-invalid` and `aria-describedby`
   - GOV.UK error pattern implementation

3. ✅ Delete confirmation
   - HTMX: `hx-confirm` confirmation dialog
   - No-JS: Form POST fallback (no confirmation - documented trade-off)
   - Dual routing: DELETE /tasks/{id} and POST /tasks/{id}/delete

4. ✅ No-JS verification script
   - Location: `wk08/lab-w8/scripts/nojs-check.md`
   - 10 test cases covering all flows

5. ✅ Trade-offs documentation
   - Location: `wk08/docs/prototyping-constraints.md`
   - Comprehensive analysis of dual-path architecture
   - Validation strategy documentation
   - Delete confirmation trade-off analysis

---

## Manual Testing Checklist

### Test 1: HTMX Validation (With JavaScript)
- [ ] Open http://127.0.0.1:8080/tasks
- [ ] Leave title field empty, click "Add Task"
- [ ] Expect: Status message "Title is required." appears without page reload
- [ ] Enter >200 characters, submit
- [ ] Expect: Status message "Title too long (max 200 characters)."
- [ ] Enter valid title, submit
- [ ] Expect: Task appears in list with success message

### Test 2: No-JS Validation (Disable JavaScript)
- [ ] Open DevTools → Settings → Disable JavaScript
- [ ] Refresh page (Ctrl+Shift+R)
- [ ] Leave title empty, click "Add Task"
- [ ] Expect: Full page reload to `/tasks?error=title`
- [ ] Expect: Error summary at top with "There is a problem"
- [ ] Expect: Input field has red border
- [ ] Expect: Error message below input
- [ ] Click error link in summary
- [ ] Expect: Focus moves to input field
- [ ] Fill in valid title, submit
- [ ] Expect: Redirect to `/tasks` with task visible

### Test 3: Delete Confirmation (With JavaScript)
- [ ] Add a task (e.g., "Test Delete")
- [ ] Click "Delete" button
- [ ] Expect: Browser confirmation dialog appears: "Delete the task 'Test Delete'?"
- [ ] Click "Cancel"
- [ ] Expect: Task remains in list
- [ ] Click "Delete" again, click "OK"
- [ ] Expect: Task disappears, status message "Deleted 'Test Delete'."

### Test 4: Delete Without Confirmation (No JavaScript)
- [ ] Disable JavaScript (DevTools)
- [ ] Refresh page
- [ ] Add a task
- [ ] Click "Delete" button
- [ ] Expect: Full page reload immediately (no confirmation)
- [ ] Expect: Task removed from list

### Test 5: Keyboard Navigation
- [ ] Tab through page: Skip link → Add form → Filter form → Tasks → Pagination
- [ ] Verify visible focus indicator on all elements
- [ ] Submit empty form with Enter key
- [ ] Tab to error link (if no-JS), press Enter
- [ ] Verify focus moves to input field

### Test 6: Screen Reader Testing (Optional - requires NVDA/Narrator)
- [ ] Enable screen reader
- [ ] Submit empty form (HTMX mode)
- [ ] Listen for: "Title is required." announcement
- [ ] Submit empty form (no-JS mode)
- [ ] Listen for: "Alert. There is a problem..." announcement
- [ ] Navigate to error link, activate
- [ ] Listen for: Input field announcement

---

## Known Behaviors (Expected)

### No-JS Delete No Confirmation ⚠️
**Documented trade-off**: No-JS path does not show confirmation dialog before delete.
- **Rationale**: Task data is low-risk, not financial/legal
- **Mitigation**: Logged in backlog for future consideration
- **Alternatives**: Add intermediate confirmation page (Week 10)

### Form State Not Preserved
**Current behavior**: Validation error in no-JS mode loses form input.
- **Why**: Redirect with query params doesn't include form data
- **Future**: Could add `?title=...` to preserve input

---

## Testing Evidence Location

**Screenshots should be saved to**:
- `wk08/lab-w8/evidence/nojs-parity/`
- Name format: `test-01-add-happy-path.png`, `test-02-validation-error.png`, etc.

**Screen reader logs** (if captured):
- `wk08/lab-w8/evidence/sr-testing.txt`

---

## Issues Found

**Log any issues in**: `backlog/backlog.csv`

**Format**:
```csv
id,week,priority,category,description,wcag,status
wk8-XX,8,high/medium/low,category,"description",WCAG-ref,open/resolved
```

**Example**:
```csv
wk8-01,8,medium,ux,"No-JS delete has no confirmation",,open
wk8-02,8,low,ux,"Form state lost on validation error (no-JS)",,open
```

---

## Next Steps

1. **Run manual tests** (above checklist)
2. **Capture screenshots** for evidence
3. **Run no-JS verification script** (`scripts/nojs-check.md`)
4. **Log any issues** in backlog
5. **Commit changes** with descriptive message
6. **Optional**: Test with screen reader (NVDA/Narrator)

---

**Tester**: __________
**Date completed**: __________
