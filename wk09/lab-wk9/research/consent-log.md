# Week 9 Peer Pilot Consent Log

**Purpose**: Track which participants gave informed consent for each pilot session.

**Privacy Note**: Do NOT record participant names or identifiable information. Use anonymous participant codes only (P1, P2, etc.).

---

## Session Tracking Table

| Session | Participant Code | Date | Time | Session ID | Consent Given? | Notes |
|---------|------------------|------|------|------------|----------------|-------|
| 1 | P1 | YYYY-MM-DD | HH:MM | (6-char hex) | ☑ Yes / ☐ No | |
| 2 | P2 | YYYY-MM-DD | HH:MM | (6-char hex) | ☑ Yes / ☐ No | |
| 3 | P3 | YYYY-MM-DD | HH:MM | (6-char hex) | ☑ Yes / ☐ No | |
| 4 | P4 | YYYY-MM-DD | HH:MM | (6-char hex) | ☑ Yes / ☐ No | |
| 5 | P5 | YYYY-MM-DD | HH:MM | (6-char hex) | ☑ Yes / ☐ No | |
| 6 | P6 | YYYY-MM-DD | HH:MM | (6-char hex) | ☑ Yes / ☐ No | |

---

## Consent Procedure Checklist

For **each session**, the facilitator must:

### Before Session Starts
- [ ] Read full informed consent script (from `protocol.md` Section 3.1)
- [ ] Explain what data is collected (timestamps, task codes, durations)
- [ ] Explain what is NOT collected (no names, no recordings, no personally identifiable information)
- [ ] Explain participant rights:
  - Right to withdraw at any time without penalty
  - Right to skip any task
  - Right to request data deletion (within 48 hours)
- [ ] Ask: "Do you understand and consent to participate?" (Verbal yes/no)
- [ ] Record consent status in table above

### During Session
- [ ] Remind participant they can stop at any time
- [ ] Do NOT pressure participant to continue if uncomfortable

### After Session
- [ ] Offer participant the option to review their metrics.csv data
- [ ] Ask: "Are you okay with us keeping your data for analysis?"
- [ ] If participant says NO: Delete their rows from metrics.csv immediately
- [ ] Record any post-session data deletion requests below

---

## Data Deletion Requests

If a participant withdraws consent or requests data deletion:

| Participant Code | Session ID | Deletion Requested | Date Deleted | Deleted By |
|------------------|------------|-------------------|--------------|------------|
| (e.g., P3) | (6-char) | YYYY-MM-DD HH:MM | YYYY-MM-DD | (Facilitator initials) |

**How to delete data**:
1. Open `data/metrics.csv`
2. Search for rows with `session_id` matching the participant's Session ID
3. Delete all matching rows
4. Save file
5. Record deletion in table above

---

## Ethical Compliance Notes

- **Anonymity**: Participant codes (P1, P2) are used ONLY in this log and pilot-notes.md. They are NOT linked to session IDs in metrics.csv.
- **Session IDs**: The 6-character hex tokens (e.g., `a3f87c`) in metrics.csv cannot be traced back to individuals without this consent log.
- **Storage**: This consent-log.md file should be stored separately from metrics.csv and NOT pushed to GitHub.
- **Retention**: Delete this consent log after final analysis is complete (end of Week 10).

---

## Summary After All Sessions

**Total sessions conducted**: _____  
**Total consents obtained**: _____  
**Total withdrawals**: _____  
**Data deletion requests**: _____  

**Facilitator signature/initials**: ___________  
**Date completed**: YYYY-MM-DD  
