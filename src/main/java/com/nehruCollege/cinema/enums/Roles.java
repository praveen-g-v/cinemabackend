package com.nehruCollege.cinema.enums;

public enum Roles {
	USER,
	STUDENT,
	FACULTTY,
	ADMIN;
	
	 public static Roles fromString(String role) {
        try {
            return Roles.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No enum constant " + Roles.class.getCanonicalName() + "." + role);
        }
    }
	 
	 public String toString(Roles role) {
		 switch(role){
			 case USER: return "USER";
			 case STUDENT: return "STUDENT";
			 case FACULTTY: return "FACULTTY";
			 case ADMIN: return "ADMIN";
		default:
			return "USER";
		 }
	 }

}
