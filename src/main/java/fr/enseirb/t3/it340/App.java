package fr.enseirb.t3.it340;
import static spark.Spark.*;

import fr.enseirb.t3.it340.servlets.VisualisationAccueil;
import fr.enseirb.t3.it340.servlets.atelier.*;
import fr.enseirb.t3.it340.servlets.ateliers.*;
import fr.enseirb.t3.it340.servlets.authentification.*;
import fr.enseirb.t3.it340.servlets.creneau.*;
import fr.enseirb.t3.it340.servlets.creneaux.VisualisationCreneaux;
import fr.enseirb.t3.it340.servlets.creneaux.VisualisationEnseignant;
import freemarker.template.Configuration;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class App {

	public static Integer getPort() throws IOException, NumberFormatException {
		Properties properties = new Properties();
		InputStream is = App.class.getClassLoader().getResourceAsStream("application.properties");
		properties.load(is);

		return Integer.parseInt(properties.getProperty("port"));
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

		// Configuration du moteur de template
		FreeMarkerEngine engine = new FreeMarkerEngine();
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(App.class, "/templates/");
		engine.setConfiguration(cfg);

		// Choix du port
		port(getPort());

		// Gestion des urls
		get("/", new VisualisationAccueil(), engine);

		// Inscription
		get("/inscription", new VisualisationInscription(), engine);
		post("/inscription", new Inscription());

		// Authentification
		get("/authentification", new VisualisationAuthentification(), engine);
		post("/authentification", new Authentification());

		// Déconnexion
		get("/deconnexion", new Deconnexion());

		// Ateliers
		get("/ateliers", new VisualisationAteliers(), engine);
		get("/ateliers/:idLabo", new VisualisationAteliersLabo(), engine);
		get("/laboratoire/ateliers", new VisualisationAteliersMonLabo(), engine);

		// Atelier
		get("/atelier/:idAtelier", new VisualisationAtelier(), engine);

		// Atelier - création
		get("/laboratoire/atelier/creer", new VisualisationCreerAtelier(), engine);
		post("/laboratoire/atelier/creer", new CreerAtelier());

		// Atelier - modification
		get("/laboratoire/atelier/:idAtelier/modifier", new VisualisationEditerAtelier(), engine);
		post("/laboratoire/atelier/:idAtelier/modifier", new EditerAtelier());

		// Atelier - suppression
		get("/laboratoire/atelier/:idAtelier/supprimer", new SupprimerAtelier());

		// Créneau - ajout
		get("atelier/:idAtelier/creneaux", new VisualisationCreneaux(), engine);
		post("atelier/:idAtelier/creneaux", new CreerCreneau());

		// Créneau - modification
		get("/atelier/:idAtelier/creneaux/:idCreneau", new VisualisationEditerCreneau(), engine);
		post("/atelier/:idAtelier/creneaux/:idCreneau", new EditerCreneau());

		// Créneau - suppression
		get("/atelier/:idAtelier/creneaux/:idCreneau/supprimer", new SupprimerCreneau());

		// Créneau - inscription
		get("/atelier/:idAtelier/creneaux/:idCreneau/inscrire", new VisualisationInscrireCreneau(), engine);
		post("/atelier/:idAtelier/creneaux/:idCreneau/inscrire", new InscrireCreneau());

		// Enregistrements
		get("/enseignant", new VisualisationEnseignant(), engine);

	}

}