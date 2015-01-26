public class Version {
    private String curVersion, newVersion;
    
    private int curMajor, curMinor, curRevision;
    private int newMajor, newMinor, newRevision;

    public Version() {
        try {
            curVersion = new FileHandle(Main.TEMP, "VERSION").read().trim();
        } catch (Exception e) {
			       curVersion = "0.0.0";
        } finally {
            String[] curv = curVersion.split (".");
            curMajor = curv.length < 1 ? 0 : Integer.valueOf(curv[0]);
            curMinor = curv.length < 2 ? 0 : Integer.valueOf(curv[1]);
            curRevision = curv.length < 3 ? 0 : Integer.valueOf(curv[2]);
        }
        
        try {
            FileHandle toCheck = new Download("https://raw.githubusercontent.com/"+Main.REPO+"/"+Main.BRANCH+"/"+Main.DIR+"/VERSION" ).get();
            newVersion = toCheck.read().trim();
            new FileHandle("VERSION").delete();
        } catch (Exception e) {
			       newVersion = curVersion;
        } finally {
            String[] newv = newVersion.split (".");
            newMajor = newv.length < 1 ? 0 : Integer.valueOf(newv[0]);
            newMinor = newv.length < 2 ? 0 : Integer.valueOf(newv[1]);
            newRevision = newv.length < 3 ? 0 : Integer.valueOf(newv[2]);
        }
    }

    public boolean isHigher () {
        if (curMajor > newMajor) return true;
        if (curMinor > newMinor) return true;
        return curRevision > newRevision;
    }
    
    public boolean isLower() {
        if (curMajor < newMajor) return true;
        if (curMinor < newMinor) return true;
        return curRevision < newRevision; 
    }
    
    public String toString() {
        return newVersion;
    }
}