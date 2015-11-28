/**
 * Classe base para audios do jogo Metodos: Construtor - Inicia o audio
 * indentificado pela string (caminho do arquivo); Play - Inicializa o audio
 * carregado; Stop - Para o audio carregado; Close - Descarta o audio carregado
 * 
 * ----------------------|NAO ALTERAR NADA NESTA
 * CLASSE|---------------------------
 **/
package funcional;

import javax.sound.sampled.*;

public class Audio {
	// Variavel que armazena o audio
	private Clip clip;
	private int frame;
	private boolean pausado;

	// Construtor
	public Audio(String s) {

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat
					.getChannels() * 2, baseFormat.getSampleRate(), false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);

			frame = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Inicia o audio
	public void play() {
		if (clip == null)
			return;
		if (!pausado)
			stop();

		clip.setFramePosition(frame);
		clip.start();
	}

	// Pausa o audio
	public void pause() {
		if (clip.isRunning()) {
			pausado = true;
			frame = clip.getFramePosition();
			clip.stop();
		}
	}

	// Para o audio
	public void stop() {
		if (clip.isRunning()) {
			frame = 0;
			clip.stop();
		}
	}

	// Descarta o audio
	public void close() {
		stop();
		clip.close();
	}

	// Altera loop
	public void loop(int loop) {
		clip.loop(loop);
	}
}
