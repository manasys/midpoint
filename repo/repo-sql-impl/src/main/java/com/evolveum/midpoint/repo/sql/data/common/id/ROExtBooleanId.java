/*
 * Copyright (c) 2010-2013 Evolveum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.evolveum.midpoint.repo.sql.data.common.id;

import com.evolveum.midpoint.repo.sql.data.common.type.RObjectExtensionType;

import java.util.Objects;

/**
 * @author lazyman
 */
public class ROExtBooleanId extends ROExtBaseId {

    private Boolean value;

    @Override
    public String getOwnerOid() {
        return super.getOwnerOid();
    }

    @Override
    public RObjectExtensionType getOwnerType() {
        return super.getOwnerType();
    }

    @Override
    public Integer getItemId() {
        return super.getItemId();
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ROExtBooleanId))
            return false;
        if (!super.equals(o))
            return false;
        ROExtBooleanId that = (ROExtBooleanId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return "ROExtBooleanId[" + ownerOid + "," + ownerType + "," + itemId + "," + value + "]";
    }
}
