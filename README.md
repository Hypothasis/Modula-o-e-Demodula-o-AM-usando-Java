# Modulação e Demodulação AM usando Java Swing
### Sobre o projeto: 
Este projeto é uma aplicação com Modulação e Demodulação AM usando Java com Swing (GUI). Esse projeto foi criado na disciplina de Sinais e Sistemas na minha faculdade de engenharia de computação, como parte da ementa da disciplina.

O projeto consiste na Modulação de uma sinal sonoro, de acordo com uma frequência escolhida pelo usuário, através da Modulação AM. A modulação AM a princípio é o transporte de um sinal com um sinal de frequência muito mais alta, assim facilitando o transporte do nosso som. É como imaginar que queremos transportar nosso áudio, ele vai de ônibus até outro local, então modulamos o sinal com uma frequência alta (o transporte), e quando "chegamos" no destino ele é demodulado, e nosso audio volta ao normal.

Esse software tem muito mais peso pelo fator educativo do que o que realmente acontece no mundo real, pois na realidade usando frequências eletromagnéticas, que não é possível produzir por software.

### Como utilizar o software
Na pasta src, temos uma pasta de arquivos .WAV, que foram disponibilizados para rodar nessa aplicação como exemplo, tendo em vista que só é possível rodar no Java Clip arquivos se áudio tipo.wav, .acc (com formato PCM 16bit). 

Você seleciona,com o botão, um áudio através do JChooseFiler
que é possível dar play, dar um loop e você saber qual é as informações do áudio.

Selecionar uma frequência é essencial para poder exportar o áudio modulado, é muito importante saber que o mesmo valor de frequência selecionada para modular tem que ser o mesmo para a frequência ao demodular.

Depois de exportar e salvar o áudio, é possível rodar um simples Soundplayer para tocar o áudio modulado, para poder escutar como o Áudio ficou.

Para demodular é o mesmo processo, seleciona o audio modulado e a mesma frequência, exporta e salva o arquivo. Assim você terá um áudio similar ao áudio inicial.

### Estrutura de pastas

/App
  /Linux
  /Windows
/Java
  /src


