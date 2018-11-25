package com.yamada.notkayla.utils;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

public class MiscTools {
    public static Throwable getCause(Throwable e) {
        Throwable cause;
        Throwable result = e;

        while(null != (cause = result.getCause())  && (result != cause) ) {
            result = cause;
        }
        return result;
    }

    public static boolean contains(String[] arr, String item) {
        for (String n : arr) {
            if (item.equals(n)) {
                return true;
            }
        }
        return false;
    }

    public static String roleListToString(List<Role> roles) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<roles.size();i++){
            Role role = roles.get(i);
            sb.append(role.getAsMention());
            if (i != roles.size()-1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
