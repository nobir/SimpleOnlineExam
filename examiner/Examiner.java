package examiner;

import base.Help;
import person.APerson;
import questions.Question;
import examiner.operations.IEOperation;

public class Examiner extends APerson implements IEOperation {

    /**
     * Properties
     */

    private static Examiner[] examiners = new Examiner[Help.EXAMINER_SIZE];
    private static int currNumOfExaminer = 0;

    public void examinerStart() {

        int choice = 0;
        boolean isAdd = false;
        boolean isDelete = false;

        String line = null;

        String tmpId = null;
        String tmpName = null;
        String tmpEmail = null;
        Examiner tmpExaminer = null;

        while (true) {
            Help.generateOptionsList(
                    new String[] { "Question", "Add Examiner", "Remove Examiner", "Show all Examiner", "Go Back" });

            if ((choice = Help.readInteger()) == -1) {
                Help.echoLn("\n\n[!] Wrong Options!!\n");
                continue;
            }

            switch (choice) {
                case 5:
                    // Go Back
                    return;

                case 1:
                    // Question
                    if (this.isEmpty()) {
                        Help.echoLn("\n\n[!] Does not have any Examiner, capacity is empty!!\n");
                        break;
                    }

                    // Input the info from console
                    while (true) {
                        Help.echo("Examiner ID: ");
                        tmpId = Help.readString();

                        if (!"".equals(tmpId))
                            break;
                    }

                    tmpExaminer = this.getExaminer(tmpId);

                    if (tmpExaminer == null) {
                        Help.echoLn("\n\n[?] Can not find Examiner!!\n");
                        break;
                    }

                    // Question
                    new Question().questionStart();

                    // Reset all the temporary variable
                    tmpExaminer = null;
                    tmpId = null;

                    break;

                case 2:
                    // Add Examiner
                    if (this.isFull()) {
                        Help.echoLn("\n\n[!] Can't Insert Examiner, capacity is full!!\n");
                        break;
                    }

                    tmpExaminer = new Examiner();

                    // Input all the info from console
                    while (true) {
                        Help.echo("Examiner ID: ");
                        tmpId = Help.readString();

                        if (!"".equals(tmpId))
                            break;
                    }

                    while (true) {
                        Help.echo("Examiner Name: ");
                        tmpName = Help.readString();

                        if (!"".equals(tmpName))
                            break;
                    }

                    while (true) {
                        Help.echo("Examiner Email: ");
                        tmpEmail = Help.readString();

                        if (!"".equals(tmpEmail))
                            break;
                    }

                    // Set all the info
                    tmpExaminer.setId(tmpId);
                    tmpExaminer.setName(tmpName);
                    tmpExaminer.setEmail(tmpEmail);

                    // Add Examiner
                    isAdd = this.addExaminer(tmpExaminer);

                    // Write in the file
                    if (isAdd) {
                        line = tmpExaminer.getId() + Help.DIVDER;
                        line += tmpExaminer.getName() + Help.DIVDER;
                        line += tmpExaminer.getEmail() + "\n";

                        Help.writeFile(Help.EXAMINER_PATH, line, true);
                    }

                    if (isAdd) {
                        Help.echoLn("\n\n[+] Successfully Added\n");
                    } else {
                        Help.echoLn("\n\n[?] Unsuccessfull to Added\n");
                    }

                    // Reset all the temporary variable
                    isAdd = false;
                    tmpExaminer = null;
                    tmpId = null;
                    tmpName = null;
                    tmpEmail = null;

                    break;

                case 3:
                    // Remove Examiner
                    if (this.isEmpty()) {
                        Help.echoLn("\n\n[!] Can not Remove Examiner, capacity is empty!!\n");
                        break;
                    }

                    // Input info from console
                    while (true) {
                        Help.echo("Examiner ID: ");
                        tmpId = Help.readString();

                        if (!"".equals(tmpId))
                            break;
                    }

                    isDelete = this.removeExaminer(tmpId);

                    if (isDelete) {
                        Help.echoLn("\n\n[-] Successfully Delete.\n");
                    } else {
                        Help.echoLn("\n\n[?] Unsuccessfull Delete\n");
                    }

                    // Reset all the temporary variable
                    isDelete = false;
                    tmpId = null;

                    break;

                case 4:
                    // Show all Examiner
                    this.showAllInformation();
                    break;

                default:
                    Help.echoLn("\n\n[!] Wrong Options!!\n");
                    break;
            }
        }
    }

    /**
     * Help Method
     */

    private boolean isEmpty() {
        return 0 >= Examiner.currNumOfExaminer;
    }

    private boolean isFull() {
        return Help.EXAMINER_SIZE <= Examiner.currNumOfExaminer;
    }

    public void initExaminer(String[] examinerInfo) {
        Examiner examiner = new Examiner();

        examiner.setId(examinerInfo[0]);
        examiner.setName(examinerInfo[1]);
        examiner.setEmail(examinerInfo[2]);

        this.addExaminer(examiner);
    }

    /**
     * Abstract Override Method
     * 
     * @see ~/person/APerson.java
     */

    public void showInformation() {
        Help.echoLn("\tExaminer ID: " + this.getId());
        Help.echoLn("\tExaminer Name: " + this.getName());
        Help.echoLn("\tExaminer Email: " + this.getEmail());
    }

    /**
     * Interface Override Method
     * 
     * @see ~/examiner/operations/IEOperation.java
     */

    public boolean addExaminer(Examiner examiner) {
        // if (this.isFull()) {
        // Help.echoLn("\nCan't Insert Examiner, capacity is full!!\n");
        // return false;
        // }

        if (this.getExaminer(examiner.getId()) == null) {
            Examiner.examiners[Examiner.currNumOfExaminer++] = examiner;
            return true;
        } else {
            Help.echo("\n\n[!] Duplication ID! can not insert!!");
        }

        return false;
    }

    public boolean removeExaminer(String examinerId) {
        // if (this.isEmpty()) {
        // Help.echoLn("\nCan't Remove Examiner, capacity is empty!!");
        // return false;
        // }

        boolean found = false;
        String line = "";
        String[] examiners = Help.readFile(Help.EXAMINER_PATH, Examiner.currNumOfExaminer);

        // For last Examiner
        if (this.getExaminer(Examiner.currNumOfExaminer - 1).getId().equals(examinerId)) {
            Examiner.examiners[Examiner.currNumOfExaminer - 1] = null;

            // Update the file
            for (int i = 0; i < examiners.length; i++) {
                if (i == examiners.length - 1) {
                    examiners[i] = "";
                }

                if (i == examiners.length - 1) {
                    line += examiners[i];
                } else {
                    line += examiners[i] + "\n";
                }
            }

            Help.writeFile(Help.EXAMINER_PATH, line, false);
            Examiner.currNumOfExaminer--;

            return true;
        }

        // For (n - 1) Examiner
        for (int i = 0; i < Examiner.currNumOfExaminer - 1; i++) {
            if (this.getExaminer(i).getId().equals(examinerId) && !found) {
                found = true;
            }

            // Rearrange examiners array
            if (found) {
                Examiner.examiners[i] = Examiner.examiners[i + 1];
            }
        }

        // Update the file
        for (int i = 0; i < examiners.length - 1; i++) {
            boolean _found = false;
            String[] examinerInfo = examiners[i].split(Help.DIVDER);

            if (examinerInfo[0].equals(examinerId) && !_found) {
                _found = true;
            }

            if (_found) {
                examiners[i] = examiners[i + 1];
            }

            line += examiners[i] + "\n";
        }

        Help.writeFile(Help.EXAMINER_PATH, line, false);

        if (!found) {
            Help.echo("\n\n[!] Can not find Examiner!!");
        } else {
            Examiner.currNumOfExaminer--;
        }

        return found;
    }

    public Examiner getExaminer(int index) {
        return Examiner.examiners[index];
    }

    public Examiner getExaminer(String examinerId) {
        for (int i = 0; i < Examiner.currNumOfExaminer; i++) {
            if (this.getExaminer(i).getId().equals(examinerId)) {
                return this.getExaminer(i);
            }
        }
        return null;
    }

    public void showAllInformation() {
        if (this.isEmpty()) {
            Help.echoLn("\n\n[!] Examiner Capacity is empty!!\n");
            return;
        }

        Help.echoLn("");

        for (int i = 0; i < Examiner.currNumOfExaminer; i++) {
            Help.echoLn("Examiner " + (i + 1) + " :");
            this.getExaminer(i).showInformation();
        }

        Help.echoLn("");
    }
}