package wintaleur.tickforge.common.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.tree.CommandNode;

import java.util.List;

public final class CommandHelp {

    private CommandHelp() {}

    /**
     * Noms des sous-commandes du nœud qui vient d'être exécuté, triés.
     *
     * <p>Se base sur le dernier nœud réellement parsé : fonctionne donc de la même
     * façon via {@code /tickforge server} et via l'alias {@code /tf server}, et
     * l'aide se met à jour toute seule quand on ajoute une sous-commande.
     */
    public static <S> List<String> childNames(CommandContext<S> context) {
        List<ParsedCommandNode<S>> nodes = context.getNodes();

        if (nodes.isEmpty()) {
            return List.of();
        }

        return childNames(nodes.get(nodes.size() - 1).getNode());
    }

    /** Noms des enfants directs d'un nœud, triés. */
    public static <S> List<String> childNames(CommandNode<S> node) {
        if (node == null) {
            return List.of();
        }

        return node.getChildren().stream()
                .map(CommandNode::getName)
                .sorted()
                .toList();
    }
}
