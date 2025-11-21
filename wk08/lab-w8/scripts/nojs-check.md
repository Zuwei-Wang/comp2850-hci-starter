# No-JS Parity Verification Script — Week 8

**Purpose**: Verify all task flows work identically with JavaScript disabled.

**Setup**:
1. Open Chrome DevTools → Settings → Preferences → Disable JavaScript
2. Alternatively: Use Firefox → Settings → Privacy & Security → Permissions → Uncheck JavaScript
3. Hard refresh (Ctrl+Shift+R / Cmd+Shift+R) to clear cached JS

---

## Test 1: Add Task (Happy Path)

**Steps**:
1. Navigate to `/tasks`
2. Enter title "No-JS test task"
3. Click "Add Task"

**Expected**:
- Full page reload (check Network tab: only HTML request)
- New task appears in list
- URL remains `/tasks` (PRG redirect)
- No error messages

**Evidence**: Screenshot of task list with new task visible.

**Result**: [ ] Pass  [ ] Fail

---

## Test 2: Add Task (Validation Error - Blank Title)

**Steps**:
1. Leave title field empty
2. Click "Add Task"

**Expected**:
- Full page reload
- URL shows `/tasks?error=title`
- Error summary appears at top: "There is a problem"
- Link to "#title" input
- Input has red border, error message below: "Title is required"
- Focus can navigate to error link and then to input

**Evidence**: Screenshot of error summary and highlighted input.

**Result**: [ ] Pass  [ ] Fail

---

## Test 3: Add Task (Validation Error - Too Long)

**Steps**:
1. Enter a title longer than 200 characters
2. Click "Add Task"

**Expected**:
- Full page reload
- URL shows `/tasks?error=title&msg=too_long`
- Error summary appears: "Title is too long (max 200 characters)"
- Input has red border with error message

**Evidence**: Screenshot of error message.

**Result**: [ ] Pass  [ ] Fail

---

## Test 4: Filter Tasks

**Steps**:
1. Add tasks "Alpha", "Bravo", "Charlie"
2. Enter "ra" in filter box
3. Click "Apply Filter" or press Enter

**Expected**:
- Full page reload
- URL shows `/tasks?q=ra&page=1`
- Only "Bravo" and "Charlie" visible (partial match)
- Result count updates: "Showing 2 of 3 tasks"

**Evidence**: Screenshot of filtered results with URL visible.

**Result**: [ ] Pass  [ ] Fail

---

## Test 5: Pagination

**Steps**:
1. Add 15 tasks (assuming page size = 10)
2. Navigate to page 2 using "Next" link

**Expected**:
- Full page reload
- URL shows `/tasks?page=2`
- Tasks 11-15 visible
- "Previous" link appears
- "Next" link disabled or hidden

**Evidence**: Screenshot of page 2 with navigation controls.

**Result**: [ ] Pass  [ ] Fail

---

## Test 6: Edit Task (inline)

**Steps**:
1. Click "Edit" on a task
2. Page reloads with edit form visible
3. Change title to "Updated via no-JS"
4. Submit

**Expected**:
- First click: Full page reload to `/tasks?editing={id}`
- Edit form appears inline
- After submit: Full page reload
- Updated title visible
- URL redirects to `/tasks`

**Evidence**: Screenshot of edit form, then updated task.

**Result**: [ ] Pass  [ ] Fail

---

## Test 7: Delete Task

**Steps**:
1. Click "Delete" button on a task

**Expected**:
- Form submits to `POST /tasks/{id}/delete`
- Full page reload
- Task removed from list
- URL redirects to `/tasks`
- **No confirmation dialog** (documented trade-off)

**Evidence**: Screenshot of task list with item removed.

**Result**: [ ] Pass  [ ] Fail

---

## Test 8: Keyboard Navigation

**Steps**:
1. Tab through entire page (skip link → add form → filter form → tasks → pagination)
2. Ensure visible focus indicator at each stop
3. Activate "Add Task" with Enter
4. Navigate to error link (if error shown) with Tab, activate with Enter

**Expected**:
- Focus order matches visual order
- All interactive elements reachable
- Enter activates links/buttons
- Focus visible on all elements (no hidden focus)

**Evidence**: Notes on tab order, screenshot of visible focus.

**Result**: [ ] Pass  [ ] Fail

---

## Test 9: Browser History

**Steps**:
1. Start at `/tasks`
2. Add task (redirects to `/tasks`)
3. Filter to "test" (reloads to `/tasks?q=test`)
4. Go to page 2 (reloads to `/tasks?q=test&page=2`)
5. Click browser Back button twice

**Expected**:
- Back #1 → `/tasks?q=test&page=1` (filtered, page 1)
- Back #2 → `/tasks?q=test` (filtered, page 1 implicit)
- Back #3 → `/tasks` (no filter)
- History stack matches user intent

**Evidence**: Notes on history behaviour.

**Result**: [ ] Pass  [ ] Fail

---

## Test 10: Error Recovery Flow

**Steps**:
1. Submit empty form (error appears)
2. Click error link in summary
3. Verify focus moves to input field
4. Fill in title and submit
5. Verify success (no error, task appears)

**Expected**:
- Error link is keyboard-accessible
- Clicking error link moves focus to `#title`
- After fixing error, form submits successfully
- Error summary disappears after successful submission

**Evidence**: Screenshots of error → focus → success.

**Result**: [ ] Pass  [ ] Fail

---

## Summary

**Passed**: _____ / 10
**Failed**: _____ / 10

**Issues found**: (log in `backlog/backlog.csv` with IDs wk8-XX)

**Notes**:
- Attach screenshots to `wk08/lab-w8/evidence/nojs-parity/`
- Update `wk08/docs/prototyping-constraints.md` with any new trade-offs discovered
- Re-run this script after any route or template changes

---

**Verified by**: __________
**Date**: __________
