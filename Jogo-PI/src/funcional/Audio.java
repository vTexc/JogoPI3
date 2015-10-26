/**
 * Classe base para audios do jogo
 * Metodos:
 * 		Construtor - Inicia o audio indentificado pela string (caminho do arquivo);
 * 		Play - Inicializa o audio carregado;
 * 		Stop - Para o audio carregado;
 * 		Close -  Descarta o audio carregado
 * 
 * ----------------------|NAO ALTERAR NADA NESTA CLASSE|---------------------------
 **/
package funcional;

import javax.sound.sampled.*;

public class Audio {
	//Variavel que armazena o audio
	private Clip clip;
	
	//Construtor
	public Audio(String s) {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass()
					.getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Inicia o audio
	public void play() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	//Para o audio
	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}
	
	//Descarta o audio
	public void close() {
		stop();
		clip.close();
	}
}
