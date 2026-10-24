# Object Diagram

One concrete snapshot of the running system, consistent with [`class-diagram.md`](class-diagram.md) and with the demo data in `db/seed.sql`: the student **Al-Shahariar ** has submitted one request with **two item lines**, the administrator has approved it, and **three units** are held by it.

Mermaid has no object-diagram type, so each box below is one *instance*: the box name is the object name and the `<<Class>>` line is its class, in place of the UML `object : Class` underline.

```mermaid
classDiagram
    direction LR

    class shahariar {
        <<User>>
        id = 2
        fullName = Al-Shahariar 
        email = shahariar@lab.edu
        role = STUDENT
    }

    class req7 {
        <<BookingRequest>>
        id = 7
        requestCode = REQ-261024143015
        studentId = 2
        studentName = Al-Shahariar 
        purpose = Digital Logic Design lab experiment 4
        returnDate = 2026-10-31
        status = APPROVED
        createdAt = 2026-10-24 14.30
    }

    class line1 {
        <<BookingItem>>
        id = 11
        requestId = 7
        materialId = 1
        materialName = Breadboard
        quantity = 2
        status = APPROVED
    }

    class line2 {
        <<BookingItem>>
        id = 12
        requestId = 7
        materialId = 2
        materialName = Logic IC
        quantity = 1
        status = APPROVED
    }

    class breadboard {
        <<Material>>
        id = 1
        subject = Digital Logic Design
        name = Breadboard
        variant = 830 points
        availableCount = 0
    }

    class logicIc {
        <<Material>>
        id = 2
        subject = Digital Logic Design
        name = Logic IC
        variant = 7400 NAND
        availableCount = 1
    }

    class bb001 {
        <<EquipmentUnit>>
        id = 1
        materialId = 1
        materialName = Breadboard
        serialNo = BB-001
        status = BOOKED
    }

    class bb002 {
        <<EquipmentUnit>>
        id = 2
        materialId = 1
        materialName = Breadboard
        serialNo = BB-002
        status = BOOKED
    }

    class ic7400a {
        <<EquipmentUnit>>
        id = 4
        materialId = 2
        materialName = Logic IC
        serialNo = IC-7400-A
        status = BOOKED
    }

    shahariar --> req7 : studentId = 2
    req7 --> line1 : requestId = 7
    req7 --> line2 : requestId = 7
    breadboard --> line1 : materialId = 1
    logicIc --> line2 : materialId = 2
    breadboard --> bb001 : materialId = 1
    breadboard --> bb002 : materialId = 1
    logicIc --> ic7400a : materialId = 2
    req7 --> bb001 : request_id = 7
    req7 --> bb002 : request_id = 7
    req7 --> ic7400a : request_id = 7
```

**Why this snapshot is internally consistent**

- Two item lines, quantities 2 and 1 → exactly three units are held, which is what `BookingDao.submit` books.
- `breadboard.availableCount = 0`: the seed has three breadboards, BB-001 and BB-002 are `BOOKED` by this request and BB-003 is `DAMAGED`.
- `logicIc.availableCount = 1`: of IC-7400-A and IC-7400-B, only IC-7400-A is held here.
- Both lines are `APPROVED` and no line is `PENDING`, so `syncRequestStatus` sets the request to `APPROVED` — the next legal action is *Record issue*, which turns all three units and the request to `ISSUED`.
- The request is listed at most once per material (`UNIQUE(request_id, material_id)`), so a second breadboard request would have raised `quantity` instead of adding a third line.

**One honest mismatch to note:** the last three links are database state, not Java fields. `equipment_units.request_id` is a column, but the `EquipmentUnit` class has no `requestId` attribute — `BookingDao.listUnitsForRequest(7)` is what resolves that link at runtime. The link is drawn because the snapshot would be unreadable without it.
