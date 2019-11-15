/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.sdk.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Printable implements Serializable {
    private static final long serialVersionUID = -9211228198328632073L;

    private transient ThreadLocal<Printable> visitor = new ThreadLocal<Printable>() {
        protected Printable initialValue() {
            return null;
        }
    };
    private String simpleName = this.getClass().getSimpleName();

    public Printable() {}

    private String toString0() {
        try {
            PropertyDescriptor[] props =
                Introspector.getBeanInfo(this.getClass(), Object.class).getPropertyDescriptors();
            Object[] params = new Object[0];
            boolean isFirst = true;
            StringBuilder strBuilder = new StringBuilder(512);
            strBuilder.append(this.getClass().getName()).append('{');
            PropertyDescriptor[] var11 = props;
            int var10 = props.length;

            for (int var9 = 0; var9 < var10; ++var9) {
                PropertyDescriptor descriptor = var11[var9];
                Method m = descriptor.getReadMethod();
                if (m != null) {
                    boolean accessible = m.isAccessible();
                    if (!accessible) {
                        m.setAccessible(true);
                    }

                    try {
                        Object result = m.invoke(this, params);
                        if (!isFirst) {
                            strBuilder.append(", ");
                        } else {
                            isFirst = false;
                        }

                        strBuilder.append(descriptor.getName()).append(": ");
                        if (result instanceof String) {
                            strBuilder.append('"');
                            strBuilder.append(result);
                            strBuilder.append('"');
                        } else {
                            strBuilder.append(result);
                        }
                    } catch (Exception e) {
                    }

                    if (!accessible) {
                        m.setAccessible(false);
                    }
                }
            }

            strBuilder.append('}');
            return strBuilder.toString();
        } catch (IntrospectionException e) {
            return super.toString();
        }
    }

    public String toString() {
        if (this.visitor.get() == null) {
            this.visitor.set(this);

            String var2;
            try {
                var2 = this.toString0();
            } finally {
                this.visitor.set(null);
            }

            return var2;
        } else {
            return this.simpleName + "@" + Integer.toHexString(this.hashCode());
        }
    }

}
