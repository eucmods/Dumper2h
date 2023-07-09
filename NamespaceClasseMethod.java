package com.dummper.cmods;
/*

ESTE CODIGO FOI CRIADO POR :
nosso Telegram
@CSTEAM69
@eucmods

QUALQUER OUTROS  SUPOSTO ENVOLVIMENTO E FAKE.
CRIADO EM 2023
*/
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.widget.EditText;
import android.view.View;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

public class NamespaceClasseMethod extends Activity {

	private File offsetFile;
	public EditText path,escrever,CM;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		path = findViewById(R.id.path);
	}
	/*
	private void addLog(String log) {
        Log.d("Console", log);
        String currentLogs = escrever.getText().toString();
        String newLogs = currentLogs + log + "\n";
        escrever.setText(newLogs);
    }*/
	public void CM(View v){
	  String filePath = path.getText().toString();
		if (filePath != null) {
			offsetFile = new File(filePath);
			File funcoesFile = new File("/storage/emulated/0/DumperUp/CSTeam.h");
			List<NamespaceClasseFuncao> dadosFuncoes = new ArrayList<>();
			dadosFuncoes.add(new NamespaceClasseFuncao("Namespace: ", "class PlayerScript : Pawn", "get_getVelocity"));
			dadosFuncoes.add(new NamespaceClasseFuncao("Namespace: ", "class PlayerScript : Pawn", "get_weaponGO"));
			dadosFuncoes.add(new NamespaceClasseFuncao("Namespace: ", "class PlayerScript : Pawn", "get_rechargingWeapon"));
			dadosFuncoes.add(new NamespaceClasseFuncao("COW", "class UIModelFriends : UIBaseModel", "IsCelebrity"));
			dadosFuncoes.add(new NamespaceClasseFuncao("COW.GamePlay", "class Player", "LateUpdate"));
			dadosFuncoes.add(new NamespaceClasseFuncao("COW.GamePlay", "class Player", "GetAttackableCenterWS"));
			dadosFuncoes.add(new NamespaceClasseFuncao("COW.GamePlay", "class Player", "get_MyFollowCamera"));
			for (NamespaceClasseFuncao ncf : dadosFuncoes) {
				 SaveDumper(offsetFile.getAbsolutePath(), funcoesFile.getAbsolutePath(), ncf);
			}
		}
	}
	
	
	
	
	
	private void SaveDumper(String LocalDump, String arquivoFuncoes, NamespaceClasseFuncao namespaceClasseFuncao)  {
		boolean encontrouNamespace = false;
		boolean encontrouClasse = false;
		boolean encontrouFuncao = false;
		String offsetAtual = "CSTEAM_&_CMODS";
		boolean salvarLinha = false;
		try{
			try (BufferedReader reader = new BufferedReader(new FileReader(LocalDump))) {
				String linha;
				while ((linha = reader.readLine()) != null){
					if (linha.contains(namespaceClasseFuncao.getNamespace())){
						encontrouNamespace = true;
					}
					else 
					if (encontrouNamespace && linha.contains(namespaceClasseFuncao.getClasse())){
						encontrouClasse = true;
					}
					else if (encontrouClasse && linha.contains(namespaceClasseFuncao.getFuncao())){
						encontrouFuncao = true;
						salvarLinha = true;
						break;
					}
					String regex = "// RVA: 0x([0-9A-Fa-f]+)";
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(linha);
					if (matcher.find()){
						offsetAtual = matcher.group(1);
					}
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		try {
			try (FileWriter writer = new FileWriter(arquivoFuncoes, true)) {
				if (new File(arquivoFuncoes).length() == 0){
					writer.write(
					"//https://t.me/eucmods\n"+ 
					"#ifndef CMODS_H\n"+ 
					"#define CMODS_H\n"+
					"struct {\n");
				}
				if (salvarLinha){
					writer.write("  uintptr_t " + namespaceClasseFuncao.getFuncao() + " = 0x" + offsetAtual + ";\n");
				}
				if (encontrouFuncao){
					writer.write("\n\n\n}\n" + "#endif");
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	private static class NamespaceClasseFuncao {
		private final String namespace;
		private final String classe;
		private final String funcao;

		public NamespaceClasseFuncao(String namespace, String classe, String funcao) {
			this.namespace = namespace;
			this.classe = classe;
			this.funcao = funcao;
		}

		public String getNamespace() {
			return namespace;
		}

		public String getClasse() {
			return classe;
		}

		public String getFuncao() {
			return funcao;
		}
	}
}