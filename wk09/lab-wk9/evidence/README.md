# Week 9 Evidence Folder

This folder contains evidence collected during peer pilot sessions for Week 9 Lab 2.

## Contents

### Required for Task 1 Submission
- `summary.md` - Brief overview of pilot findings (complete after sessions)
- `metrics-backup-P*.csv` - Backup copies of metrics.csv per participant
- `screenshots/` - Screenshots showing key interface states during pilots

### Supporting Materials (Not for submission)
- Full `pilot-notes.md` is in `../research/` (too detailed for submission)
- Full `consent-log.md` is in `../research/` (contains sensitive linking info)
- Raw `data/metrics.csv` (anonymized excerpts only for submission)

---

## After Completing All Pilot Sessions

1. **Create `summary.md`**:
   - Write 5-6 sentence overview of pilot sessions
   - Include key findings (3-5 bullet points)
   - Highlight most critical usability issue discovered

2. **Copy metrics backups**:
   ```powershell
   Copy-Item data\metrics.csv wk09\lab-wk9\evidence\metrics-backup-P1.csv
   ```
   (Repeat for each participant: P2, P3, P4, P5, P6)

3. **Take screenshots**:
   - Filter in action (Task T1)
   - Inline edit mode (Task T2)
   - Delete confirmation modal (Task T4)
   - Validation error message (if encountered)

4. **Redact sensitive data** (if needed):
   - Replace actual session IDs with placeholders (e.g., `[P1_session]`)
   - Remove any accidental PII from screenshots

---

## File Naming Conventions

- `metrics-backup-P1.csv` - Participant 1's metrics
- `screenshot-filter-in-action.png` - Descriptive filename
- `screenshot-validation-error.png` - Clear, specific names

---

**Last Updated**: (Fill in after sessions complete)
