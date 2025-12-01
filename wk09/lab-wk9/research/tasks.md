# Evaluation Tasks — Week 9

## Task T1: Filter Tasks

**Task Code**: `T1_filter`

**Scenario**:
"You need to find all tasks containing the word 'report'. Use the filter box to show only matching tasks, then count how many tasks remain."

**Setup**:
- Pre-populate task list with 10 tasks
- 3 tasks must contain "report" in title:
  - "Submit expense report"
  - "Draft annual report"
  - "Review quarterly report"
- 7 other unrelated tasks (e.g., "Buy groceries", "Call dentist")

**Success Criteria**:
- Participant uses filter box (types "report")
- Participant reports correct count (3 tasks)
- Completed within 2 minutes
- No validation errors

**Metrics**:
- **Time-on-task**: Milliseconds from task start to stating count
- **Completion**: 0 = fail, 1 = success
- **Validation errors**: Count of errors triggered
- **Confidence rating** (1–5): "How confident are you that you found all matching tasks?"

**Accessibility Checks**:
- ✓ Result count announced by screen reader?
- ✓ Keyboard-only completion possible?
- ✓ Works with JS disabled (submit button functions)?

**Expected Outcome**: Filter narrows list to 3 tasks, "Showing 3 of 10 tasks matching 'report'" visible

---

## Task T2: Edit Task Title

**Task Code**: `T2_edit`

**Scenario**:
"The task 'Draft report' needs to be updated. Change it to 'Submit report by Friday' and save the change."

**Setup**:
- Task visible in list with title "Draft report"
- Participant must activate edit mode, change text, save

**Success Criteria**:
- Participant activates edit mode (clicks "Edit" button)
- Participant updates title to "Submit report by Friday" (or close match)
- Change persists after save (title updates in list)
- Completed within 90 seconds
- No validation errors

**Metrics**:
- **Time-on-task**: Milliseconds from clicking "Edit" to save confirmation
- **Completion**: 0/1
- **Validation errors**: Count (e.g., blank title submitted)
- **Confidence rating** (1–5): "How confident are you the change was saved?"

**Accessibility Checks**:
- ✓ Status message "Updated [title]" announced after save?
- ✓ Focus remains on/near edited task?
- ✓ Works with keyboard only (Tab, Enter)?
- ✓ Works with JS disabled (page reloads with updated title)?

**Expected Outcome**: Task title updates, status message "Task 'Submit report by Friday' updated successfully." appears

---

## Task T3: Add New Task

**Task Code**: `T3_add`

**Scenario**:
"You need to remember to 'Call supplier about delivery'. Add this as a new task to your list."

**Setup**:
- Add task form visible at top of page
- Form may be empty or have placeholder text

**Success Criteria**:
- Participant types title "Call supplier about delivery" (or close match)
- Submits form successfully
- New task appears in task list
- Completed within 60 seconds

**Metrics**:
- **Time-on-task**: Milliseconds from focus in input to task appearing in list
- **Completion**: 0/1
- **Validation errors**: Count (if they accidentally submit blank)
- **Confidence rating** (1–5): "How confident are you the task was added?"

**Accessibility Checks**:
- ✓ Status message "Added [title]" announced?
- ✓ Form remains usable after error (if validation triggered)?
- ✓ Works with JS disabled (PRG redirect, task visible)?

**Expected Outcome**: New task "Call supplier about delivery" appears in list, status message confirms addition

---

## Task T4: Delete Task

**Task Code**: `T4_delete`

**Scenario**:
"The task 'Test entry' is no longer needed. Delete it from the list."

**Setup**:
- Task "Test entry" visible in list
- Delete button clearly labeled

**Success Criteria**:
- Participant clicks "Delete" button
- **HTMX path**: Confirms deletion in dialog
- **No-JS path**: Form submits immediately (no confirmation)
- Task removed from list
- Completed within 45 seconds

**Metrics**:
- **Time-on-task**: Milliseconds from click "Delete" to task removal
- **Completion**: 0/1
- **Confirmation acknowledged** (HTMX only): Yes/No
- **Confidence rating** (1–5): "How confident are you the task was deleted?"

**Accessibility Checks**:
- ✓ Delete button has accessible name ("Delete task: Test entry")?
- ✓ Status message "Deleted [title]" announced (HTMX)?
- ✓ Works with keyboard only?
- ✓ Works with JS disabled (immediate deletion, documented trade-off)?

**Expected Outcome**: Task "Test entry" disappears from list, status message confirms deletion (HTMX) or page reloads without task (no-JS)

---

## Task Order & Timing

**Recommended Sequence**:

1. **Warm-up** (not timed, ~1 min):
   - "Browse the task list and familiarize yourself with the interface. Let me know when you're ready to start."

2. **T3 (Add)** — 60s max
   - Low cognitive load, builds confidence

3. **T1 (Filter)** — 120s max
   - Medium complexity, tests search

4. **T2 (Edit)** — 90s max
   - Tests inline interaction, validation

5. **T4 (Delete)** — 45s max
   - Destructive action, tests confirmation

6. **Debrief** (qualitative, ~3 min):
   - "What did you find most confusing?"
   - "Was there anything you wanted to do but couldn't figure out?"
   - "How did the interface compare to other task managers you've used?"

**Total time**: ~10-12 minutes per participant (including consent, setup, debrief)

**Counterbalancing**: If testing multiple participants, alternate T1/T2 order to avoid learning effects

---

## Facilitator Notes

### Do NOT Help Unless:
- Participant is completely stuck (>3 min on single task)
- Participant explicitly asks for help
- **If you intervene**: Mark task as "facilitated" in observations, note what help was given

### Think-Aloud Protocol (Optional):
- Ask participants to narrate their thoughts if comfortable
- Don't force - some people find it distracting
- Example prompt: "As you work, feel free to say what you're thinking"

### Special Conditions:

**Screen Reader Users**:
- Allow extra time for navigation (1.5× time limits)
- Log SR-specific observations separately
- Note: which announcements were helpful vs missing

**Keyboard-Only**:
- Offer keyboard-only variant to 1-2 participants
- Log visible focus indicators
- Note: any elements unreachable via Tab

**No-JS Mode**:
- Test at least 1 participant with JS disabled
- Compare completion times with JS-on participants
- Verify PRG redirects work correctly
- Note: any functionality gaps

---

## Success Definitions

**Completion Codes**:
- `1` = Task fully completed, correct outcome
- `0.5` = Partial completion (e.g., found filter but wrong count)
- `0` = Failed or abandoned

**Time Bounds** (prompt if exceeded):
- T1: 120 seconds
- T2: 90 seconds
- T3: 60 seconds
- T4: 45 seconds

**Prompt text**: "Would you like to continue with this task, or shall we move to the next one?"

**Validation errors**: Count from server logs (`step=validation_error`)

---

## Data Collection

**Per Task**:
- [ ] Time-on-task (ms) — from server logs
- [ ] Completion (0/0.5/1) — manual observation
- [ ] Validation errors (count) — from server logs
- [ ] Confidence rating (1–5) — verbal question
- [ ] Facilitator notes (free text) — observations, quotes

**Per Session**:
- [ ] Session ID (anonymous token)
- [ ] JS mode (on/off/unknown)
- [ ] Assistive technology used (SR/magnifier/none)
- [ ] Post-session satisfaction (optional UMUX-Lite)

---

## Pre-Task Setup Checklist

Before each participant session:

1. [ ] Clear browser cache and cookies
2. [ ] Generate new anonymous session ID
3. [ ] Pre-populate task list with test data (see Setup sections above)
4. [ ] Start server with logging enabled
5. [ ] Open task list page in browser
6. [ ] Have stopwatch ready (backup timing)
7. [ ] Have consent form and task sheets printed/ready

---

## Post-Task Cleanup

After each participant session:

1. [ ] Export `data/metrics.csv` to backup location
2. [ ] Clear task list (reset to default state)
3. [ ] Review facilitator notes for clarity
4. [ ] Transfer confidence ratings to results spreadsheet

