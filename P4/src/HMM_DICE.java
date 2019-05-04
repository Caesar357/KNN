public class HMM_DICE {
    private double[] initialPick;
    private double[][] transitionProbability;
    private double[][] emissionProbability;
    private int[] ObservationSequence;

    public HMM_DICE(){
        this.initialPick = new double[] {1.0 / 3, 1.0 / 3, 1.0 / 3};
    }

    public void setInitialPick(double[] initialPick) {
        this.initialPick = initialPick;
    }

    public double[] getInitialPick() {
        return initialPick;
    }

    public void setTransitionProb(double[][] transitionProbability) {
        this.transitionProbability = transitionProbability;
    }

    public double[][] getTransitionProbability() {
        return transitionProbability;
    }

    public void setEmissionProb(double[][] emissionProbability) {
        this.emissionProbability = emissionProbability;
    }

    public double[][] getEmissionProb() {
        return emissionProbability;
    }

    public void setObservationSeq(int[] observationSequence) {
        ObservationSequence = observationSequence;
    }

    public int[] getObservationSequence() {
        return ObservationSequence;
    }
    
    public void printSeq(int[] sequence){
        for (int i = 0; i < sequence.length; i++) {
            int dice = sequence[i] + 1; 
            System.out.print("D" + dice + "\t");
            if((i + 1) % 10 == 0){
                System.out.println();
            }
        }
    }

    public int getMaxProDice(double[] probability){
        int dice = -1;
        double MaxProbability = 0;
        for (int i = 0; i < probability.length; i++) {
            if (probability[i] > MaxProbability) {
                MaxProbability = probability[i];
                dice = i;
            }
        }
        System.out.println("Sequence Probability: " + MaxProbability);
        return dice;
    }

    public void evaluate(int[] ObservationSequence) {
        if (ObservationSequence.length == 0) {
            System.out.println("No input!");
            return;
        }
        int[] dieSequence = new int[ObservationSequence.length];
        double[][] PathProbability = new double[ObservationSequence.length][transitionProbability.length];
        int[][] Parent = new int[ObservationSequence.length][transitionProbability.length];
        PathProbability[0][0] = emissionProbability[0][ObservationSequence[0] - 1] * initialPick[0]; 
        PathProbability[0][1] = emissionProbability[1][ObservationSequence[0] - 1] * initialPick[1]; 
        PathProbability[0][2] = emissionProbability[2][ObservationSequence[0] - 1] * initialPick[2]; 
        for (int t = 1; t < ObservationSequence.length; t++) {
            for (int j = 0; j < transitionProbability.length; j++) {
                int dice = -1;
                double maxProbability = 0;
                for (int i = 0; i < transitionProbability.length; i++) {
                    double temp = PathProbability[t - 1][i] * transitionProbability[i][j] * emissionProbability[j][ObservationSequence[t] - 1];
                    if (temp > maxProbability) {
                        maxProbability = temp;
                        dice = i;
                    }
                }
                PathProbability[t][j] = maxProbability;
                Parent[t][j] = dice; 
            }
        }
        dieSequence[dieSequence.length - 1] = getMaxProDice(PathProbability[dieSequence.length - 1]);
        for (int t = dieSequence.length - 2; t >= 0; t--) {
            dieSequence[t] = Parent[t+1][dieSequence[t+1]];
        }
        System.out.println("Best Possible Dies Sequence: ");
        printSeq(dieSequence);
    }

    public static void main (String[] args) {
        HMM_DICE HMM_dice = new HMM_DICE();
        String filename = "InputFile1.txt";
        System.out.println("Input File: " + filename);
        fileReader.readFileByLines(filename, HMM_dice);
        HMM_dice.evaluate(HMM_dice.getObservationSequence());
    }

    public static void test1(){
        HMM_DICE HMM_dice = new HMM_DICE();
        double[] initialPick = new double[] {0.2, 0.4, 0.4};
        double[][] transitionProbability = new double[][] {{0.5, 0.2, 0.3}, {0.3, 0.5, 0.2}, {0.2, 0.3, 0.5}};
        double[][] emissionProbability = new double[][] {{0.5, 0.5}, {0.4, 0.6}, {0.7, 0.3}};
        int[] ObservationSequence = new int[] {1, 2, 1};
        HMM_dice.setInitialPick(initialPick);
        HMM_dice.setTransitionProb(transitionProbability);
        HMM_dice.setEmissionProb(emissionProbability);
        HMM_dice.setObservationSeq(ObservationSequence);
        HMM_dice.evaluate(HMM_dice.getObservationSequence());
    }
}