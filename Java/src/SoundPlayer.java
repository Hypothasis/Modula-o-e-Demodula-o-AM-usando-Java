
public class SoundPlayer implements Runnable {
	
	private String Musica;
	
	public SoundPlayer(String Song) {
		this.Musica = Song;
	}
	
	@Override
    public void run() {
        System.out.println("O SoundPlayer está tocando um som...");
    }
}
