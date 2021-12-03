public class Instruction {

    public final String nom;

    public final int valeur;

    Instruction(String string) {
        this.nom = string.split(" ")[0];
        this.valeur = Integer.parseInt(string.split(" ")[1]);
    }
}
