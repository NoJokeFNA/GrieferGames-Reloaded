package net.griefergames.reloaded.config;

import lombok.Getter;
import net.griefergames.reloaded.GrieferGamesReloaded;
import net.griefergames.reloaded.exception.ExceptionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
public class PropertiesReader {

    private final Properties dataSourceProperties;
    private final String fileName;

    public PropertiesReader() {
        this.dataSourceProperties = new Properties();
        this.fileName = GrieferGamesReloaded.PLUGIN.getPlugin().getDataFolder().getAbsolutePath() + "\\" + "datasource.properties";
    }

    public void loadDataSourceProperties() {
        this.createDataSourceProperties();

        try ( BufferedInputStream inputStream = new BufferedInputStream( new FileInputStream( this.fileName ) ) ) {
            dataSourceProperties.load( inputStream );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Error while loading '" + this.fileName + "'" );
        }
    }

    private void createDataSourceProperties() {
        try {
            final File file = new File( this.fileName );
            if ( file.createNewFile() ) {
                System.out.println( file.mkdirs() );

                this.fillFile( file );
                System.out.println( "File successfully created" );
                return;
            }

            System.out.println( "File does already exists" );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Error while creating '" + this.fileName + "'" );
        }
    }

    private void fillFile( final File file ) {
        try ( FileWriter fileWriter = new FileWriter( file ); InputStream inputStream = this.getFileFromResourceAsStream( this.fileName ) ) {
            for ( String output : this.getConfigInput( inputStream ) )
                fileWriter.write( output + System.lineSeparator() );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Error while filling '" + this.fileName + "'" );
        }
    }

    private InputStream getFileFromResourceAsStream( final String fileName ) {
        final ClassLoader classLoader = GrieferGamesReloaded.class.getClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream( fileName );
        if ( inputStream == null )
            throw new IllegalArgumentException( "InputStream is null! " + fileName );
        else
            return inputStream;
    }

    private List<String> getConfigInput( final InputStream inputStream ) {
        final List<String> inputList = new ArrayList<>();

        try ( InputStreamReader streamReader = new InputStreamReader( inputStream, StandardCharsets.UTF_8 ); BufferedReader reader = new BufferedReader( streamReader ) ) {
            reader.lines().forEach( inputList::add );
        } catch ( IOException exception ) {
            exception.printStackTrace();
        }

        return inputList;
    }

}