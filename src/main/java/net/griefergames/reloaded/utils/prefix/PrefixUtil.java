package net.griefergames.reloaded.utils.prefix;

public class PrefixUtil {

    private static int i = 0;

    public static String setPlayerPrefix( final char firstColor, final char secondColor ) {
        final StringBuilder stringBuilder = new StringBuilder();

        final char[] playerNameCharArray = "NoJokeFNA".toCharArray();
        for ( char c : playerNameCharArray ) {
            i++;

            switch ( i ) {
                case 1:
                case 2:
                    stringBuilder.append( "§" ).append( firstColor ).append( c ).append( " " );
                    break;

                case 3:
                    stringBuilder.append( "§" ).append( secondColor ).append( c ).append( " " );
                    break;

                case 4:
                    stringBuilder.append( "§" ).append( secondColor ).append( c ).append( " " );

                    i = 0;
                    break;
            }
        }

        i = 0;

        return stringBuilder.toString();
    }
}
