package design.semicolon.instagram.helpers;

import android.content.Context;
import android.graphics.Color;

import design.semicolon.instagram.R;
import design.semicolon.instagram.models.Comment;
import design.semicolon.instagram.models.User;

/**
 * Created by dsaha on 2/6/16.
 */
public class Decorator {

    public static String getDecorizedUsername (Context context, User user) {

        int color = ((context.getResources().getColor(R.color.dark_blue)) & 0x00FFFFFF);
        String hex = "#"+Integer.toHexString(color);

        if (user == null ) {
            return null;
        }

        String htmlUsernameString =  "<strong><font color='"+hex+"'>"+ user.getUsername() +"</font></strong>";
        return htmlUsernameString;
    }

    public static String getDecorizedText (Context context, String string) {

        int color = ((context.getResources().getColor(R.color.light_blue)) & 0x00FFFFFF);
        String hex = "#"+Integer.toHexString(color);

        StringBuilder htmlCommentStringBuffer = new StringBuilder();

        if (string  == null) {
            return null;
        }

        String[] words = string.split("\\s+");

        for (String word:words) {
            if (word.length()>0 && (word.charAt(0) == '@'||word.charAt(0) == '#')){
                String decoratedWord =  "<font color='"+hex+"'>"+ word +"</font>";
                htmlCommentStringBuffer.append(decoratedWord);
            } else {
                htmlCommentStringBuffer.append(word);
            }

            if (word != words[words.length -1]){
                htmlCommentStringBuffer.append(" ");
            }
        }

        return htmlCommentStringBuffer.toString();
    }
}
