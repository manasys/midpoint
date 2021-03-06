/**
 * Copyright (c) 2017-2018 Evolveum
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
package com.evolveum.midpoint.security.enforcer.api;

import javax.xml.namespace.QName;

import com.evolveum.midpoint.prism.PrismObject;
import com.evolveum.midpoint.prism.delta.ObjectDelta;
import com.evolveum.midpoint.prism.util.ObjectDeltaObject;
import com.evolveum.midpoint.util.ShortDumpable;
import com.evolveum.midpoint.util.exception.SchemaException;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

/**
 * @author semancik
 *
 */
public class AuthorizationParameters<O extends ObjectType, T extends ObjectType> implements ShortDumpable {
	
	@SuppressWarnings("rawtypes")
	public static final AuthorizationParameters<ObjectType,ObjectType> EMPTY = new AuthorizationParameters<>(null, null, null);
	
	// ODO specifies authorization object with delta
	private final ObjectDeltaObject<O> odo;
	private final PrismObject<T> target;
	private final QName relation;
	
	private AuthorizationParameters(ObjectDeltaObject<O> odo, PrismObject<T> target, QName relation) {
		super();
		this.odo = odo;
		this.target = target;
		this.relation = relation;
	}
	
	public ObjectDeltaObject<O> getOdo() {
		return odo;
	}

	public boolean hasObject() {
		return odo != null && odo.hasAnyObject();
	}
	
	public PrismObject<O> getOldObject() {
		if (odo == null) {
			return null;
		}
		return odo.getOldObject();
	}
	
	public PrismObject<O> getNewObject() {
		if (odo == null) {
			return null;
		}
		return odo.getNewObject();
	}
	
	public PrismObject<O> getAnyObject() {
		if (odo == null) {
			return null;
		}
		return odo.getAnyObject();
	}
	
	public ObjectDelta<O> getDelta() {
		if (odo == null) {
			return null;
		}
		return odo.getObjectDelta();
	}
	
	public boolean hasDelta() {
		return odo != null && odo.getObjectDelta() != null;
	}

	public PrismObject<T> getTarget() {
		return target;
	}

	public QName getRelation() {
		return relation;
	}

	@Override
	public String toString() {
		return "AuthorizationParameters(odo=" + odo + ", target=" + target
				+ ", relation=" + relation + ")";
	}
	
	@Override
	public void shortDump(StringBuilder sb) {
		sb.append("odo=");
		if (odo == null) {
			sb.append("null");
		} else {
			sb.append("(");
			sb.append(odo.getOldObject()).append(",");
			sb.append(odo.getObjectDelta()).append(",");
			sb.append(odo.getNewObject());
			sb.append(")");
		}
		sb.append(",");
		shortDumpElement(sb, "target", target);
		shortDumpElement(sb, "relation", relation);
		if (sb.length() > 1) {
			sb.setLength(sb.length() - 2);
		}
	}

	private void shortDumpElement(StringBuilder sb, String label, Object o) {
		if (o != null) {
			sb.append(label).append("=").append(o).append(", ");
		}
	}


	public static class Builder<O extends ObjectType, T extends ObjectType> {
		private ObjectDeltaObject<O> odo;
		private PrismObject<O> newObject;
		private ObjectDelta<O> delta;
		private PrismObject<O> oldObject;
		private PrismObject<T> target;
		private QName relation;
		
		public Builder<O,T> newObject(PrismObject<O> object) {
			if (odo != null) {
				throw new IllegalArgumentException("Odo already set, cannot set object");
			}
			this.newObject = object;
			return this;
		}
		
		public Builder<O,T> delta(ObjectDelta<O> delta) {
			if (odo != null) {
				throw new IllegalArgumentException("Odo already set, cannot set delta");
			}
			this.delta = delta;
			return this;
		}
		
		public Builder<O,T> oldObject(PrismObject<O> object) {
			if (odo != null) {
				throw new IllegalArgumentException("Odo already set, cannot set object");
			}
			this.oldObject = object;
			return this;
		}
		
		public Builder<O,T> odo(ObjectDeltaObject<O> odo) {
			if (oldObject != null) {
				throw new IllegalArgumentException("Old object already set, cannot set ODO");
			}
			if (delta != null) {
				throw new IllegalArgumentException("Delta object already set, cannot set ODO");
			}
			if (newObject != null) {
				throw new IllegalArgumentException("New object already set, cannot set ODO");
			}
			this.odo = odo;
			return this;
		}
		
		public Builder<O,T> target(PrismObject<T> target) {
			this.target = target;
			return this;
		}
		
		public Builder<O,T> relation(QName relation) {
			this.relation = relation;
			return this;
		}
		
		public AuthorizationParameters<O,T> build() throws SchemaException {
			if (odo == null) {
				if (oldObject == null && delta == null && newObject == null) {
					return new AuthorizationParameters<>(null, target, relation);
				} else {
					ObjectDeltaObject<O> odo = new ObjectDeltaObject<>(oldObject, delta, newObject);
					odo.recomputeIfNeeded(false);
					return new AuthorizationParameters<>(odo, target, relation);
				}
			} else {
				return new AuthorizationParameters<>(odo, target, relation);
			}
		}
		
		public static <O extends ObjectType> AuthorizationParameters<O,ObjectType> buildObjectAdd(PrismObject<O> object) {
			// TODO: Do we need to create delta here?
			ObjectDeltaObject<O> odo = new ObjectDeltaObject<>(null, null, object);
			return new AuthorizationParameters<>(odo, null, null);
		}
		
		public static <O extends ObjectType> AuthorizationParameters<O,ObjectType> buildObjectDelete(PrismObject<O> object) {
			// TODO: Do we need to create delta here?
			ObjectDeltaObject<O> odo = new ObjectDeltaObject<>(object, null, null);
			return new AuthorizationParameters<>(odo, null, null);
		}
		
		public static <O extends ObjectType> AuthorizationParameters<O,ObjectType> buildObjectDelta(PrismObject<O> object, ObjectDelta<O> delta) throws SchemaException {
			ObjectDeltaObject<O> odo;
			if (delta != null && delta.isAdd()) {
				odo = new ObjectDeltaObject<>(null, delta, object);
			} else {
				odo = new ObjectDeltaObject<>(object, delta, null);
				odo.recomputeIfNeeded(false);
			}
			return new AuthorizationParameters<>(odo, null, null);
		}

		public static <O extends ObjectType> AuthorizationParameters<O,ObjectType> buildObject(PrismObject<O> object) {
			ObjectDeltaObject<O> odo = new ObjectDeltaObject<>(object, null, object);
			return new AuthorizationParameters<>(odo, null, null);
		}
		
	}
	
}
