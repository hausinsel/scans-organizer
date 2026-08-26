public class LlmMock {
    public static int PLACE_FOR_FILES = 100;
    String[] listOfFiles;

    LlmMock(String[] listOfFiles) {
        if(listOfFiles.length > PLACE_FOR_FILES) {
            System.err.println("Mehr Dateien als Platz im Array!");
            System.exit(1);
        }

        this.listOfFiles = listOfFiles;
    }

    String[][] analyzeFiles(){
        //{{"filepath", "category"}}
        //ToDo: dynamisch machen
        String[][] ret = new String[PLACE_FOR_FILES][2];
        for(int i = 0; i < this.listOfFiles.length; i++) {
            ret[i][0] = this.listOfFiles[i];
        }
        return ret;
    }
}
