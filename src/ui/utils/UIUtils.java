/**
 * 
 */
package ui.utils;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

public final class UIUtils {
	private static JFileChooser direcotryChooser = null;
	
	public static final class PathResourceExcel{
		public static final String EXCEL_CLIENTS = "/resources/excels/model_client.xlsx";
		public static final String EXCEL_FOURNISSEURS = "/resources/excels/model_fournisseur.xlsx";
		public static final String EXCEL_PRODUITS = "/resources/excels/model_produit.xlsx";
		public static final String EXCEL_ACHATS = "/resources/excels/model_achat.xlsx";
		public static final String EXCEL_ACHATCONTIENTPRODUITS = "/resources/excels/model_achat.xlsx";
	}
	
	private UIUtils() {
	}
	
	public static File createAndPrepareEmptyExcelFile( Component owner, String patternFileName, String pathToResource ) {
		return createAndPrepareEmptyFile(owner, patternFileName, pathToResource, "xlsx");
	}
	
	public static File createAndPrepareEmptyPDFFile( Component owner, String patternFileName) {
		return createAndPrepareEmptyFile(owner, patternFileName, null, "pdf");
	}
	
	private static File createAndPrepareEmptyFile( Component owner, String patternFileName, String pathToResource, String fileExtension ) {
		File file = null;
		
		try {
			if (direcotryChooser == null) {
				direcotryChooser = new JFileChooser();
				direcotryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
			}
			
			int result = direcotryChooser.showOpenDialog(owner);

			File directory = null;
			if (result == JFileChooser.APPROVE_OPTION) {
				directory = direcotryChooser.getSelectedFile();
			}
			
			if (directory == null || !directory.isDirectory()) {
				return file;
			}
			
			direcotryChooser.setCurrentDirectory(directory);
			
			String dateTime = utils.DateUtils.getDateTimeOfThisMoment().replaceAll(":", "-");
			file = new File( directory.getAbsolutePath() + "/"+patternFileName+"_"+dateTime+"."+fileExtension);
			file.createNewFile();
			
			if (pathToResource != null) {
				utils.FilesAndLaunchUtils.copyFileResource(pathToResource, file.getAbsolutePath());
			}
		} catch (Exception e) {
		}
		
		return file;
	}
}
