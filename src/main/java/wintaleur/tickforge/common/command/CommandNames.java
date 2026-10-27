package wintaleur.tickforge.common.command;

import java.util.List;

/**
 * Source unique de vérité pour les noms de l'arbre de commandes TickForge.
 *
 * <pre>
 * /tickforge  (alias /tf)
 *   ├── server  ... branche exécutée côté serveur (mod/main)
 *   └── client  ... branche exécutée côté client  (mod/client)
 * </pre>
 */
public final class CommandNames {

    private CommandNames() {}

    /** Racine de la branche. */
    public static final String ROOT = "tickforge";

    /** Alias court de la racine. */
    public static final String ALIAS = "tf";

    /** Sous-branche serveur : /tf server ... */
    public static final String SERVER = "server";

    /** Sous-branche client : /tf client ... */
    public static final String CLIENT = "client";

    /** /tf client menu */
    public static final String MENU = "menu";

    /**
     * Sous-commandes de la branche client.
     *
     * <p>Constat : quand client et serveur partagent la même racine (/tf), les
     * sous-commandes client n'apparaissent pas dans les suggestions du chat, alors
     * qu'elles s'exécutent bien. Le serveur en publie donc un miroir (voir
     * {@code TickForgeCommands#clientMirror}) pour que le chat les connaisse.
     *
     * <p>À tenir à jour quand tu ajoutes une commande client : le client affiche un
     * avertissement au démarrage si cette liste et la vraie branche divergent.
     */
    public static final List<String> CLIENT_COMMANDS = List.of(MENU);
}
