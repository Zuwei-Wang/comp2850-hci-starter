# Week 9 Lab 2: Peer Pilot Session Guide

## Quick Start

This guide walks you through running 5-6 peer pilot sessions to evaluate your task management interface.

---

## Before You Start

### Prerequisites
- [ ] Server is running (`gradlew run` at http://127.0.0.1:8080)
- [ ] `data/metrics.csv` exists with headers
- [ ] Task list is seeded with test data (see `protocol.md` Section 4.2)
- [ ] You have 5-6 willing participants (classmates, peers)
- [ ] You have read `protocol.md` in full

### Materials Checklist
- [ ] `consent-log.md` - For tracking consent
- [ ] `pilot-notes.md` - For recording observations
- [ ] `data-collection.md` - For recording ratings and times
- [ ] Stopwatch/timer (phone or computer)
- [ ] Quiet space for conducting sessions

---

## Running a Pilot Session (15-20 minutes per participant)

### Step 1: Pre-Session Setup (5 minutes)

1. **Generate Session ID**:
   ```powershell
   # PowerShell:
   -join ((48..57) + (97..102) | Get-Random -Count 6 | ForEach-Object {[char]$_})
   ```
   Or online tool: https://www.random.org/strings/
   - Format: 6 characters, lowercase hex (e.g., `a3f87c`)

2. **Set Session Cookie**:
   - Open http://127.0.0.1:8080 in browser
   - Open DevTools (F12) → Application/Storage → Cookies
   - Add new cookie:
     - Name: `sid`
     - Value: `(your 6-char session ID)`
     - Path: `/`

3. **Seed Task Data**:
   - Ensure these tasks exist in the list:
     - "Draft report" (for T2 edit task)
     - "Test entry" (for T4 delete task)
     - At least 3 tasks containing "report" (for T1 filter task)
   - Add tasks manually via interface or backend if needed

4. **Verify Logging**:
   - Perform a test action (e.g., filter by "test")
   - Check `data/metrics.csv` - should contain a new row with your session ID

5. **Record Session Info**:
   - Fill in `consent-log.md` table with participant code, date, time, session ID

---

### Step 2: Introduction (2 minutes)

**Say to participant**:

> "Hi! Thank you for helping me test this task management interface. This session will take about 15-20 minutes. Here's what will happen:
> 
> 1. I'll explain what data is collected and get your consent.
> 2. You'll have 2 minutes to explore the interface.
> 3. You'll complete 4 short tasks (adding, filtering, editing, deleting).
> 4. After each task, I'll ask you to rate your confidence.
> 5. At the end, I'll ask a few quick questions.
> 
> **What I'm collecting**:
> - Timestamps of your actions
> - Task completion times
> - Validation errors (like blank title)
> - No video, no audio, no screen recording
> 
> **What I'm NOT collecting**:
> - Your name
> - Your student ID
> - Any personal information
> 
> **Your rights**:
> - You can stop at any time without penalty
> - You can skip any task
> - You can request your data be deleted (within 48 hours)
> 
> **Do you understand and consent to participate?**"

**Record consent**: Check ☑ or ☐ in `consent-log.md`

If participant says **no**, thank them and end the session (no data collected).

---

### Step 3: Warm-up (2 minutes)

**Say to participant**:

> "Take 2 minutes to explore the interface. Click around, see what's there. This is not timed, and I won't be recording anything during this warm-up. Feel free to ask questions."

**Observe**: Note in `pilot-notes.md` what the participant does during warm-up (e.g., clicked edit, tried pagination, etc.)

---

### Step 4: Task T3 - Add New Task (60 seconds)

**Say to participant**:

> "Okay, here's your first task. You have 60 seconds. I'll start the timer when you're ready.
> 
> **Scenario**: You need to remember to call your supplier about a delivery. Add a new task with the title: 'Call supplier about delivery'
> 
> Ready? Go!"

**Start stopwatch** when participant begins.

**Observe**:
- How quickly they locate the add button
- Do they hesitate before submitting?
- Do they trigger validation errors?

**Stop stopwatch** when:
- ✅ Success: Task appears in list
- ❌ Failed: 60 seconds elapsed, task not added
- ⏸ Gave Up: Participant says they can't do it

**After task**:
- Ask: "On a scale of 1-5, how confident are you that you completed this task correctly?" (1 = very uncertain, 5 = very confident)
- Record time, status, confidence in `data-collection.md`
- Note observations in `pilot-notes.md`

---

### Step 5: Task T1 - Filter Tasks (120 seconds)

**Say to participant**:

> "Next task. You have 2 minutes.
> 
> **Scenario**: You want to find all tasks related to reports. Use the filter to search for 'report' and tell me how many matching tasks you see.
> 
> Ready? Go!"

**Start stopwatch**.

**Observe**:
- Do they find the filter input?
- Do they notice live filtering?
- Do they count correctly? (Expected: 3 tasks)

**Stop stopwatch** when:
- ✅ Success: Participant says correct count (3)
- ❌ Failed: Participant says wrong count or can't find filter
- ⏸ Gave Up: Participant stops trying

**After task**:
- Ask confidence rating (1-5)
- Record time, status, correct count, confidence in `data-collection.md`

---

### Step 6: Task T2 - Edit Existing Task (90 seconds)

**Say to participant**:

> "Next task. You have 90 seconds.
> 
> **Scenario**: Your boss wants the report by Friday. Change the task 'Draft report' to 'Submit report by Friday'
> 
> Ready? Go!"

**Start stopwatch**.

**Observe**:
- Do they locate the edit button quickly?
- Do they use keyboard (Enter) or mouse (Save)?
- Do they trigger validation errors?

**Stop stopwatch** when:
- ✅ Success: Task text updated to "Submit report by Friday"
- ❌ Failed: 90 seconds elapsed, task not updated

**After task**:
- Ask confidence rating (1-5)
- Record data in `data-collection.md`

---

### Step 7: Task T4 - Delete Task (45 seconds)

**Say to participant**:

> "Last task. You have 45 seconds.
> 
> **Scenario**: The 'Test entry' task was added by mistake. Delete it.
> 
> Ready? Go!"

**Start stopwatch**.

**Observe**:
- Do they find the delete button?
- Do they read the confirmation modal?
- Do they seem hesitant?

**Stop stopwatch** when:
- ✅ Success: "Test entry" task removed from list
- ❌ Failed: 45 seconds elapsed, task still visible

**After task**:
- Ask confidence rating (1-5)
- Record data in `data-collection.md`

---

### Step 8: Debrief (3 minutes)

**Ask open-ended questions** (record in `pilot-notes.md`):

1. "What was your overall impression of the interface?"
2. "What worked well?"
3. "What was confusing or difficult?"
4. "Do you have any suggestions for improvement?"

**Ask UMUX-Lite questions** (record in `data-collection.md`):

1. "On a scale of 1-7, to what extent do you agree: 'This system's capabilities meet my requirements.'" (1 = Strongly Disagree, 7 = Strongly Agree)
2. "On a scale of 1-7, to what extent do you agree: 'This system is easy to use.'"

**Optional**: Ask about satisfaction (1-7 scale)

---

### Step 9: Post-Session Cleanup (2 minutes)

1. **Export metrics.csv backup**:
   ```powershell
   Copy-Item data\metrics.csv wk09\lab-wk9\evidence\metrics-backup-P1.csv
   ```
   (Replace `P1` with participant code)

2. **Reset task list** (if needed for next session):
   - Re-add "Draft report" and "Test entry" tasks
   - Ensure 3+ tasks with "report" exist

3. **Review notes**:
   - Transfer handwritten notes to `pilot-notes.md`
   - Fill in observations, quotes, timestamps

4. **Thank participant**:
   > "Thank you so much for your time! Your feedback is really helpful. Remember, you can request data deletion within 48 hours by contacting me."

---

## After All Sessions

### Step 10: Aggregate Data

1. **Calculate metrics** (in `data-collection.md`):
   - Completion rates per task
   - Median/mean time-on-task
   - Validation error rates
   - Average confidence ratings
   - UMUX-Lite scores

2. **Analyze qualitative data** (in `pilot-notes.md`):
   - Common successes (patterns across participants)
   - Common struggles (recurring issues)
   - Quotes worth highlighting
   - Design insights

3. **Review automated logs** (`data/metrics.csv`):
   - Verify all sessions captured
   - Check for unexpected patterns (e.g., high error rates on specific tasks)
   - Cross-reference with manual notes

---

## Troubleshooting

**Problem**: Metrics.csv not logging events
- **Solution**: Check server logs, verify MetricsLogger.init() ran, ensure session cookie is set

**Problem**: Participant can't see status updates
- **Solution**: Check browser console for errors, verify HTMX loaded, ensure ARIA live regions present

**Problem**: Session takes longer than expected
- **Solution**: Gently guide stuck participants after 3 minutes, offer to skip task

**Problem**: Participant encounters a bug
- **Solution**: Note the bug in `pilot-notes.md`, offer participant option to continue or stop

---

## Special Test Variants

### Keyboard-Only Testing
- Ask participant to use **only keyboard** (no mouse)
- Increase time limits by 20% (T3: 72s, T1: 144s, T2: 108s, T4: 54s)
- Focus on Tab order, focus indicators, keyboard traps

### Screen Reader Testing
- Ask participant to use screen reader (NVDA/JAWS/VoiceOver)
- Increase time limits by 50% (T3: 90s, T1: 180s, T2: 135s, T4: 68s)
- Record SR software used
- Focus on ARIA announcements, form errors, status updates

### No-JS Testing
- Disable JavaScript in browser (DevTools → Settings → Disable JavaScript)
- Test PRG pattern (edit/add should redirect)
- T4 delete will use POST /tasks/{id}/delete (no modal confirmation)

---

## Evidence Pack for Task 1 (Due Week 9)

After completing all sessions, assemble:

1. **Summary document** (`wk09/lab-wk9/evidence/summary.md`):
   - Brief overview of pilot sessions (5-6 sentences)
   - Key findings (3-5 bullet points)
   - Most critical usability issue discovered

2. **Quantitative data** (`data-collection.md`):
   - Completion rates table
   - Time-on-task table
   - UMUX-Lite scores

3. **Qualitative highlights** (from `pilot-notes.md`):
   - 3-5 representative quotes
   - Common patterns observed

4. **Metrics sample** (1-2 rows from `metrics.csv`):
   - Redacted session IDs if needed

**Do NOT include**:
- Full `pilot-notes.md` (too detailed, may contain sensitive observations)
- `consent-log.md` (contains linking info)
- Participant names or identifiable information

---

**Good luck with your pilot sessions!**

If you have questions, refer to `protocol.md` for detailed ethical guidelines.
