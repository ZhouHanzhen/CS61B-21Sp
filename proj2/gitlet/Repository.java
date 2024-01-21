package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here
import java.util.Date;

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The commit object directory. */
    public static final File COMMITS_FOLDER = join(GITLET_DIR, "commits");

    /** The staging area directory. */
    public static final File STAGE_FOLDER = join(GITLET_DIR, "stage");

    /** The file blobs directory. */
    public static final File BLOBS_FOLDER = join(GITLET_DIR, "blobs");

    /** Shared initial Commit. */
    public static Commit INITIAL_COMMIT = null;

    /** The master branch. */
    public static String master = null;

    /** The current branch. */
    public static String HEAD = null;




    /* TODO: fill in the rest of this class. */

    /** Creates a new Gitlet version-control system in the current directory. */
    public static void init() {
        // Create the .gitlet directory in CWD
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
        } else {
            Utils.message("A Gitlet version-control system already exists in the current directory.;");
            System.exit(0);
        }

        // initialize COMMITS_FOLDER, STAGE_FOLDER, BLOBS_FOLDER
        if (!COMMITS_FOLDER.exists()) {
            COMMITS_FOLDER.mkdir();
        }
        if (!STAGE_FOLDER.exists()) {
            STAGE_FOLDER.mkdir();
        }
        if (!BLOBS_FOLDER.exists()) {
            BLOBS_FOLDER.mkdir();
        }

        // Create the initial commit
        String m = "initial commit";
        Date time = new Date();
        time.setTime(0); //00:00:00 UTC, Thursday, 1 January 1970
        Commit initial = new Commit(time, m, null, null);

        // Adds the initial commit to COMMIT_FOLDER
        initial.saveCommit();

        // Share the initial commit
        INITIAL_COMMIT = initial;

        master = initial.getsha1id();
        HEAD = master;
    }


}
