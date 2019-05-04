import java.io.*;
import java.util.ArrayList;

public class fileReader {
    public static  void readFileByLines(String fileName, HMM_DICE HMM_dice) {
        File file = new File(fileName);
        BufferedReader reader = null;
        ArrayList<String> lines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmpString = null;
            while ((tmpString = reader.readLine()) != null) {
                if(tmpString.charAt(0) != '#') {
                    lines.add(tmpString);
                }
            }
            reader.close();
            double p = Double.valueOf(lines.get(0));
            double q = (1 - p) / 2;
            double[][] transitionProbability = {
                    {p, q, q},
                    {q, p, q},
                    {q, q, p}
            };
            HMM_dice.setTransitionProb(transitionProbability);
            double[][] emissionProbability = new double[3][3];
            String[] tempEmission;
            for (int i = 1; i < 4; i++) {
                tempEmission = lines.get(i).split(",");
                for (int j = 0; j < tempEmission.length; j++) {
                    emissionProbability[i - 1][j] = Double.valueOf(tempEmission[j]);
                }
            }
            HMM_dice.setEmissionProb(emissionProbability);
            String[] tempObservation;
            tempObservation = lines.get(4).substring(1,lines.get(4).length() - 1).split(",");
            int[] ObservationSequence = new int[tempObservation.length];
            for (int i = 0; i < tempObservation.length; i++){
                ObservationSequence[i] = Integer.valueOf(tempObservation[i].trim());
            }
            HMM_dice.setObservationSeq(ObservationSequence);          
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}