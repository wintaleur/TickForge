package wintaleur.tickforge.client.gui;

import dev.vellum.mod.client.VellumScreens;
import net.minecraft.client.Minecraft;
import wintaleur.tickforge.server.TickForge;

/**
 * Point d'entrée unique pour ouvrir un écran TickForge (une page Vellum).
 *
 * <p><b>Ce que cette classe ne peut pas faire :</b> empêcher un débordement toute seule.
 * La mise en page est calculée par Vellum à partir du HTML/CSS, pas par Java. Le
 * garde-fou est donc dans {@code main.scss} (classes {@code tf-screen}, {@code tf-fill},
 * {@code tf-scroll}) et chaque page doit s'en servir.
 *
 * <p><b>Ce qu'elle fait :</b>
 * <ul>
 *   <li>un seul endroit pour ouvrir un écran (plus de {@code VellumScreens.open} éparpillés) ;</li>
 *   <li>ouverture différée sur le thread client (sinon le chat, qui se ferme juste après
 *       l'exécution de la commande, refermerait aussi l'écran qu'on vient d'ouvrir) ;</li>
 *   <li>journalise la taille logique réelle de la fenêtre à chaque ouverture, pour savoir
 *       ce que la page a vraiment comme place ;</li>
 *   <li>avertit si cette taille est sous le plancher de conception.</li>
 * </ul>
 *
 * <p>Taille logique = taille de la fenêtre / échelle d'interface (1080p en GUI x4 = 480x270).
 */
public final class TickForgeScreen {

    /**
     * Plancher de conception : toute page doit rester utilisable à cette taille logique.
     * C'est le minimum que Minecraft vise pour ses propres écrans quand l'échelle
     * d'interface est élevée.
     */
    public static final int MIN_WIDTH = 320;
    public static final int MIN_HEIGHT = 240;

    private TickForgeScreen() {}

    /**
     * @param page chemin de la page dans les assets du mod, ex. {@code "menu.html"}
     */
    public static void open(String page) {
        Minecraft client = Minecraft.getInstance();

        client.execute(() -> {
            logViewport(page, client);
            VellumScreens.open(TickForge.MOD_ID + ":" + page);
        });
    }

    private static void logViewport(String page, Minecraft client) {
        var window = client.getWindow();
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();

        TickForge.LOGGER.info(
                "Écran {} : viewport logique {}x{} (échelle GUI {})",
                page, width, height, window.getGuiScale()
        );

        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            TickForge.LOGGER.warn(
                    "Viewport {}x{} sous le plancher de conception {}x{} : la page {} défilera ou sera tronquée.",
                    width, height, MIN_WIDTH, MIN_HEIGHT, page
            );
        }
    }
}
