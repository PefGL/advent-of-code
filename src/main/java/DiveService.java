import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class DiveService {

    public int recupererPosition(final File inputs) throws IOException {
        Coordonnees coordonnees = new Coordonnees(0, 0);
        List<Instruction> instructions = recupererListeInstructionsDepuisFichier(inputs);
        for (Instruction instruction : instructions) {
            coordonnees = InstructionStrategy.of(instruction.nom).executer(coordonnees, instruction.valeur);
        }
        return coordonnees.calculerValeurFinale();
    }

    private record Coordonnees(int profondeur, int avancementHorizontal) {

        public int calculerValeurFinale(){
            return profondeur*avancementHorizontal;
        }

    }

    private enum InstructionStrategy {
        MONTER("up", (coordonnees, valeur) -> new Coordonnees(coordonnees.profondeur() - valeur, coordonnees.avancementHorizontal())), //
        DESCENDRE("down", (coordonnees, valeur) -> new Coordonnees(coordonnees.profondeur() + valeur, coordonnees.avancementHorizontal())), //
        AVANCER("forward", (coordonnees, valeur) -> new Coordonnees(coordonnees.profondeur(), coordonnees.avancementHorizontal() + valeur));

        private final String nom;

        private final BiFunction<Coordonnees, Integer, Coordonnees> fonction;

        InstructionStrategy(final String nom, final BiFunction<Coordonnees, Integer, Coordonnees> fonction) {
            this.nom = nom;
            this.fonction = fonction;
        }

        public static InstructionStrategy of(final String instruction) {
            String[] composantesInstruction = instruction.split(" ");
            String nomInstruction = composantesInstruction[0];
            return Arrays.stream(InstructionStrategy.values())//
                .filter(instructionStrategy -> instructionStrategy.nom.equals(nomInstruction))//
                .findFirst()//
                .orElseThrow(() -> new IllegalArgumentException("Instruction inconnue"));
        }

        public Coordonnees executer(final Coordonnees coordonnees, final int valeurInsctruction) {
            return fonction.apply(coordonnees, valeurInsctruction);
        }
    }

    private List<Instruction> recupererListeInstructionsDepuisFichier(final File inputs) throws IOException {
        return Files.lines(Paths.get(inputs.toURI()))//
            .map(Instruction::new)//
            .toList();
    }
}
