/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.bliss.nplay.service.eventv3.thrift;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum EEventType implements org.apache.thrift.TEnum {
BANNER(1),
POPUP(2),
HEADER(3);

private final int value;

private EEventType(int value) {
this.value = value;
}

/**
 * Get the integer value of this enum value, as defined in the Thrift IDL.
 */
public int getValue() {
return value;
}

/**
 * Find a the enum type by its integer value, as defined in the Thrift IDL.
 * @return null if the value is not found.
 */
public static EEventType findByValue(int value) {
switch (value) {
case 1:
return BANNER;
case 2:
return POPUP;
case 3:
return HEADER;
default:
return null;
}
}
}

