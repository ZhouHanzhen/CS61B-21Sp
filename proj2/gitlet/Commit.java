package gitlet;

// TODO: any imports you need here
import static gitlet.Utils.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.Calendar;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    /** The timestamp of this Commit. */
    private Date timestamp;

    /** The map of file names to blob references(SHA-1 ids). */
    private HashMap<String, String> nameTofiles;

    /** The parent reference(SHA-1 id) and the second parent reference(SHA-1 id) of this commit. */
    private ArrayList<String> parentsID; //因为commits的最终会形成图，用邻接表来表示图会更好

    /** The message of this Commit. */
    private String message;



    /* TODO: fill in the rest of this class. */

    /** Create a commit object with the specified parameters. */
    public Commit(Date date, String msg, HashMap<String, String> filesMap, ArrayList<String> parents) {
        timestamp = date;
        message = msg;
        nameTofiles = filesMap;
        parentsID = parents;
    }

    /**
     * Saves a commit to a file for future use.
     */
    public void saveCommit() {
        // get the sha1 id of this commit
        String id = Utils.sha1(this);

        // name this commit using sha1-id
        File uniqueCommit = Utils.join(Repository.COMMITS_FOLDER, id);

        if (!uniqueCommit.exists()) {
            try {
                uniqueCommit.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // serialize this commit
        Utils.writeObject(uniqueCommit, this);
    }

    /** Reads in and deserialize a commit from a file with sha1-id in COMMITS_DIR. */
    public static Commit fromFile(String id) {
        File file = Utils.join(Repository.COMMITS_FOLDER, id);
        Commit commit = Utils.readObject(file, Commit.class);
        return commit;
    }

    /** get the sha1-id of a commit. */
    public String getsha1id() {
        return Utils.sha1(this);
    }


}
