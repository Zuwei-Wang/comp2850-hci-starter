# Metrics Definitions — Week 9

Reference: `../../../references/evaluation-metrics-quickref.md`

---

## Objective Metrics

### 1. Completion Rate

**Definition**: Proportion of participants who successfully complete the task.

**Calculation**:
```
Completion rate = (# successes) / (# attempts)
```

**Data Source**:
- Manual observation (facilitator marks 0/0.5/1)
- Server logs confirmation (look for `step=success` in metrics.csv)

**Reporting**:
- Percentage per task (e.g., "T1: 4/5 = 80%")
- Split by condition: JS-on vs JS-off, keyboard-only vs mouse, screen reader vs visual

**Success Codes**:
- `1` = Task fully completed, correct outcome
- `0.5` = Partial completion (e.g., found filter but reported wrong count)
- `0` = Failed or abandoned

**Example**:
```
T1 (Filter Tasks):
  Overall: 4/5 = 80%
  JS-on: 3/3 = 100%
  JS-off: 1/2 = 50%
```

**HCI Insight**: Completion rate < 80% indicates major usability issue requiring immediate attention.

---

### 2. Time-on-Task

**Definition**: Duration from task start to completion or abandonment.

**Calculation**:
- **Primary**: Server-timed (ms column in metrics.csv) — start timestamp to success/error event
- **Backup**: Facilitator stopwatch — start when participant reads scenario, stop when they say "done"

**Reporting**:
- **Median** (primary metric): Middle value, resistant to outliers
- **MAD** (Median Absolute Deviation): Robust measure of spread
- **Range**: Min-max for context
- **Mean ± SD** (supplementary): For comparison with literature

**Formula (MAD)**:
```
MAD = median(|x_i - median(x)|)
```

**Example**:
```
T1 (Filter Tasks):
  Median: 24s
  MAD: 6s
  Range: 12s–58s (n=5)
  Mean: 28s ± 18s
```

**Split By**:
- JS-on vs JS-off (expect no-JS slower due to full page reloads)
- Keyboard-only vs mouse+keyboard
- Screen reader vs visual

**Time Bounds** (for prompting):
- T1 (Filter): 120s
- T2 (Edit): 90s
- T3 (Add): 60s
- T4 (Delete): 45s

**HCI Insight**: Large MAD suggests inconsistent experience — some participants found it easy, others struggled. Investigate qualitative notes for patterns.

---

### 3. Validation Error Rate

**Definition**: Proportion of task attempts that trigger validation errors (blank input, max length exceeded, etc.).

**Calculation**:
```
Error rate = (# validation_error events) / (# total attempts)
```

**Data Source**: `data/metrics.csv` where `step=validation_error`

**Reporting**:
- Percentage per task
- Error type breakdown (from `notes` column)

**Example**:
```
T3 (Add Task):
  Error rate: 2/5 = 40%
  Errors:
    - 1× blank title (participant clicked "Add Task" before typing)
    - 1× title too long (participant pasted 250-character text)
```

**HCI Insight**: High error rate (>20%) suggests:
- Poor affordances (unclear what's required)
- Insufficient feedback (constraints not communicated)
- Accessibility issue (screen reader users didn't hear hint text)

---

### 4. Validation Error Count Per Participant

**Definition**: Average number of validation errors each participant encounters per task.

**Calculation**: Count rows in `metrics.csv` with `step=validation_error` for given session + task

**Reporting**: Mean errors per participant per task

**Example**:
```
T2 (Edit Task):
  Mean errors per participant: 0.4
  (2 errors total across 5 participants)
  
  Breakdown:
    - P1: 0 errors
    - P2: 1 error (blank title on first attempt)
    - P3: 0 errors
    - P4: 1 error (exceeded max length)
    - P5: 0 errors
```

**HCI Insight**: Errors clustered in specific participants → need to understand their context (keyboard-only? screen reader? no-JS?).

---

### 5. HTTP Status Codes

**Definition**: Server response codes logged during task attempts.

**Data Source**: `http_status` column in metrics.csv

**Codes**:
- `200 OK`: Success
- `400 Bad Request`: Validation error
- `500 Internal Server Error`: Server-side bug (should not occur in evaluation)

**Reporting**: Count of non-200 responses per task

**Example**:
```
T3 (Add Task):
  200 OK: 3
  400 Bad Request: 2 (validation errors)
  500: 0
```

**HCI Insight**: Any 500 errors indicate bugs requiring immediate fix before continuing pilots.

---

## Subjective Metrics

### 6. Confidence Rating

**Definition**: Self-reported confidence that task was completed correctly.

**Scale**: 1 (not at all confident) → 5 (very confident)

**Collection Method**: Ask immediately after each task:
> "On a scale of 1 to 5, how confident are you that you completed that task correctly?"

**Reporting**:
- Mean ± standard deviation per task
- Distribution (count of each rating: 1, 2, 3, 4, 5)

**Example**:
```
T1 (Filter Tasks):
  Mean confidence: 4.2 ± 0.8
  Distribution:
    1 (not confident): 0
    2: 0
    3 (neutral): 1
    4: 2
    5 (very confident): 2
```

**HCI Insight**: Low confidence despite successful completion → interface doesn't provide sufficient feedback. High confidence despite failure → misleading affordances.

**Pattern to watch**:
- Task completed + confidence 5 = Good UX
- Task completed + confidence 2–3 = Needs better feedback
- Task failed + confidence 4–5 = Misleading design
- Task failed + confidence 1–2 = At least user knows they failed

---

### 7. Difficulty Rating (Optional)

**Definition**: Perceived difficulty of the task.

**Scale**: 1 (very easy) → 7 (very difficult)

**Collection Method**: Ask after each task:
> "How difficult was that task? 1 is very easy, 7 is very difficult."

**Reporting**: Mean ± SD per task

**Example**:
```
T2 (Edit Task):
  Mean difficulty: 3.4 ± 1.5
  (Most participants found it moderately easy)
```

**HCI Insight**: Difficulty rating correlates with completion time and errors. High difficulty + low completion → usability problem.

---

### 8. Post-Session Satisfaction (UMUX-Lite)

**Method**: 2-question Ultra-Simple Usability Metric (UMUX-Lite)

**Questions** (7-point scale: 1=strongly disagree, 7=strongly agree):
1. "This system's capabilities meet my requirements."
2. "This system is easy to use."

**Calculation**:
```
UMUX-Lite score = average of Q1 and Q2
```

**Interpretation**:
- 6–7: Excellent perceived usability
- 5–6: Good
- 4–5: Acceptable
- <4: Poor, needs improvement

**Example**:
```
Participant P1:
  Q1 (meets requirements): 6
  Q2 (easy to use): 5
  UMUX-Lite score: 5.5 (Good)

Overall (n=5):
  Mean UMUX-Lite: 5.2 ± 0.9
```

**HCI Insight**: UMUX-Lite takes <30 seconds, validated proxy for SUS (System Usability Scale). Scores <4 indicate systemic usability issues.

**Reference**: [Lewis et al. (2013)](https://uxpajournal.org/umux-lite/) — Validation study

---

## Qualitative Observations

### 9. Facilitator Notes

**Capture**:
- Verbatim quotes ("I don't know if it saved")
- Observed behavior (hesitation, backtracking, confusion)
- Accessibility issues (screen reader didn't announce, focus lost)
- Workarounds (used browser back button instead of cancel)
- Positive feedback ("Oh, that's nice!")

**Format**: Free text per task, tagged with task code

**Example**:
```
T2_edit | P3 | "I clicked Edit but nothing happened" 
  → HTMX request slow (3s), no loading indicator
  → Participant clicked Edit 3× thinking it was broken

T1_filter | P5 (SR user) | Screen reader announced "Found 3 tasks matching 'report'"
  → Positive: result count accessible
```

**HCI Insight**: Qualitative notes explain the "why" behind quantitative patterns. Essential for redesign decisions.

---

### 10. Accessibility Confirmations

**Per Task, Document**:
- [ ] Keyboard-only completion possible? (Tab, Enter, Esc)
- [ ] Status messages announced by screen reader?
- [ ] Focus management correct? (focus stays on/near changed element)
- [ ] Works with JS disabled (no-JS parity)?
- [ ] Loading indicators visible (for HTMX requests)?
- [ ] Error messages accessible (aria-invalid, aria-describedby)?

**Format**: Yes/No/Partial with notes

**Example**:
```
T2 (Edit Task) | Accessibility Checks:
  ✓ Keyboard-only: Yes (Tab to Edit, Enter to save)
  ✗ Status announced: No (screen reader silent after save)
  ⚠ Focus management: Partial (focus moves to top of page, should stay on task)
  ✓ No-JS parity: Yes (page reloads with updated title)
```

**HCI Insight**: Accessibility failures often correlate with general usability issues (e.g., poor feedback benefits everyone, not just screen reader users).

---

## Data Collection Templates

### Per-Task Data Sheet

```
Task: _____ | Participant: _____ | Session ID: _____ | JS Mode: on/off

Time-on-task:
  Start: __:__:__
  End: __:__:__
  Duration (calculated): ____ seconds

Completion:
  [ ] 1 = Success
  [ ] 0.5 = Partial
  [ ] 0 = Fail

Validation Errors: ____ (count)

Confidence Rating (1–5): _____

Difficulty Rating (1–7): _____ (optional)

Facilitator Notes:
_______________________________________________
_______________________________________________
```

### Post-Session Data Sheet

```
Participant: _____ | Session ID: _____ | Date: _____

UMUX-Lite:
  Q1 (meets requirements, 1–7): _____
  Q2 (easy to use, 1–7): _____
  Score (average): _____

General Feedback:
  What did you find most confusing?
  _______________________________________________

  Was there anything you wanted to do but couldn't?
  _______________________________________________

  How does this compare to other task managers?
  _______________________________________________

Assistive Technology Used:
  [ ] None
  [ ] Screen reader (specify: _______)
  [ ] Magnifier
  [ ] Keyboard-only
  [ ] Other: _____

Demographics (optional, anonymous):
  Experience with task managers: Novice / Intermediate / Expert
  Age range: 18-24 / 25-34 / 35-44 / 45-54 / 55+
```

---

## Analysis Plan

### Quantitative Analysis (Week 10)

1. **Completion rates**: Calculate per task, overall
2. **Time-on-task**: Median, MAD, range per task
3. **Error rates**: Calculate per task, identify patterns
4. **Confidence vs completion**: Cross-tabulate (are successful tasks also high confidence?)
5. **JS-on vs JS-off**: Compare completion times and rates
6. **UMUX-Lite**: Mean score, flag participants <4

### Qualitative Analysis (Week 10)

1. **Thematic coding**: Group facilitator notes by theme (validation confusion, status announcement missing, etc.)
2. **Accessibility findings**: List confirmed vs failed checks
3. **Critical incidents**: Identify moments of confusion, frustration, delight
4. **Quotes**: Extract verbatim quotes illustrating themes

### Synthesis (Week 10)

Combine quantitative + qualitative:
- **Low completion + high errors + notes about "didn't know if it saved"** → Insufficient feedback (high priority fix)
- **Long median time + notes about "had to try multiple times"** → Discoverability issue
- **High completion + low confidence** → Confirmation feedback missing

**Output**: Prioritized backlog for Week 10 redesign

---

## Ethical Considerations

### Privacy by Design

**What we log**:
- ✅ Anonymous session ID (random token)
- ✅ Task code (T1, T2, T3, T4)
- ✅ Timestamps (ISO 8601 format)
- ✅ Timing data (milliseconds)
- ✅ HTTP status codes
- ✅ JS mode (on/off)

**What we do NOT log**:
- ❌ Names, emails, usernames
- ❌ IP addresses
- ❌ Device fingerprints
- ❌ Real task content (titles entered by participants)
- ❌ Third-party analytics cookies

**Data retention**:
- Logs stored locally in `data/metrics.csv`
- **NOT** pushed to public GitHub (in .gitignore)
- Participants can request deletion (remove rows with their session ID)

**Consent**:
- Informed consent obtained before evaluation
- Participants told what data is logged and why
- Opt-out honored (data deleted immediately)

**Reference**: `../../../references/consent-pii-faq.md` for full guidance

---

## Summary

**Objective Metrics** (quantitative):
1. Completion rate (%)
2. Time-on-task (median, MAD, range)
3. Validation error rate (%)
4. Error count per participant (mean)
5. HTTP status codes (count)

**Subjective Metrics** (qualitative):
6. Confidence rating (mean ± SD, 1–5)
7. Difficulty rating (mean ± SD, 1–7)
8. UMUX-Lite (mean score, 1–7)

**Qualitative Data**:
9. Facilitator notes (verbatim quotes, observations)
10. Accessibility confirmations (Yes/No/Partial)

**Analysis**: Combine quantitative patterns with qualitative insights to identify usability issues and prioritize fixes.

