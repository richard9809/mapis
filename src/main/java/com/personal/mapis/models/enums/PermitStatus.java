package com.personal.mapis.models.enums;

public enum PermitStatus {
    PENDING_AGENCIES,   // waiting for ENV/FIRE/HEALTH
    READY_TO_ISSUE,     // all agencies completed, ready to finalize
    ISSUED,             // permit issued
    REJECTED            // optional, if something fails badly
}
