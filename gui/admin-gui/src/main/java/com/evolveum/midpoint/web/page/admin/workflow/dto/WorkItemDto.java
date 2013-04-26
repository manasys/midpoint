/*
 * Copyright (c) 2012 Evolveum
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://www.opensource.org/licenses/cddl1 or
 * CDDLv1.0.txt file in the source code distribution.
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 *
 * Portions Copyrighted 2012 [name of copyright owner]
 */

package com.evolveum.midpoint.web.page.admin.workflow.dto;

import com.evolveum.midpoint.web.component.util.Selectable;
import com.evolveum.midpoint.wf.api.WorkItem;

import java.text.DateFormat;

/**
 * @author lazyman
 */
public class WorkItemDto extends Selectable {

    public static final String F_NAME = "name";
    public static final String F_OWNER_OR_CANDIDATES = "ownerOrCandidates";
    public static final String F_CREATED = "created";

    WorkItem workItem;

    public WorkItemDto(WorkItem workItem) {
        this.workItem = workItem;
    }

    public String getName() {
        return workItem.getName();
    }

    public String getOwner() {
        return workItem.getAssignee();
    }

    public WorkItem getWorkItem() {
        return workItem;
    }

    public String getCandidates() {
        return workItem.getCandidates();
    }

    public String getCreated() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.DEFAULT);
        if (workItem.getCreateTime() != null) {
            return dateFormat.format(workItem.getCreateTime());
        } else {
            return null;
        }
    }

    public String getOwnerOrCandidates() {
        if (workItem.getAssigneeName() != null) {
            return workItem.getAssigneeName();
        } else if (workItem.getAssignee() != null) {
            return workItem.getAssignee();      // todo ???
        } else if (workItem.getCandidates() != null) {
            return workItem.getCandidates();
        } else {
            return null;
        }
    }
}
