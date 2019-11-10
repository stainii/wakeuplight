
// How to set the board up:
//
// bluetooth: 
// * gnd pin to -
// * VC pin to +
// * TX pin to RX on Arduino
// * RX pin to TX on Arudino
//
// led strip
// When using only a few pixels, the led strip can be powered by the arduino itself. Otherwise, use a separate power supply.
// * GND pin to -
// * VC pin to +
// * Din pin to an 300 - 500 ohm resistor
// * The resistor to Arduino pin 6

// BEFORE UPLOADING, UNPLUG THE CABLES FROM THE RX AND TX PORT ON THE ARDUINO. RECONNECT AFTER UPLOAD.
// TIPS FOR ADAFRUIT NEOPIXEL
// - Add 1000 uF CAPACITOR between NeoPixel strip's + and - connections.
// - MINIMIZE WIRING LENGTH between microcontroller board and first pixel.
// - NeoPixel strip's DATA-IN should pass through a 300-500 OHM RESISTOR.
// - AVOID connecting NeoPixels on a LIVE CIRCUIT. If you must, ALWAYS
//   connect GROUND (-) first, then +, then data.

#include <Adafruit_NeoPixel.h>

// Which pin on the Arduino is connected to the NeoPixels?
#define LED_PIN    6

// How many NeoPixels are attached to the Arduino?
#define LED_COUNT 3

// Declare our NeoPixel strip object:
Adafruit_NeoPixel strip(LED_COUNT, LED_PIN, NEO_GRB + NEO_KHZ800);
// Argument 1 = Number of pixels in NeoPixel strip
// Argument 2 = Arduino pin number (most are valid)
// Argument 3 = Pixel type flags, add together as needed:
//   NEO_KHZ800  800 KHz bitstream (most NeoPixel products w/WS2812 LEDs)
//   NEO_KHZ400  400 KHz (classic 'v1' (not v2) FLORA pixels, WS2811 drivers)
//   NEO_GRB     Pixels are wired for GRB bitstream (most NeoPixel products)
//   NEO_RGB     Pixels are wired for RGB bitstream (v1 FLORA pixels, not v2)
//   NEO_RGBW    Pixels are wired for RGBW bitstream (NeoPixel RGBW products)


// setup() function -- runs once at startup --------------------------------
void setup() {
  Serial.begin(9600);
  strip.begin();           // INITIALIZE NeoPixel strip object (REQUIRED)
  turnOff();
  strip.setBrightness(255); // (max = 255)
}

// loop() function -- runs repeatedly as long as board is on ---------------
void loop() {  
  if (Serial.available() > 0) {
    char incomingValue = Serial.read();  // read bluetooth
    if(incomingValue == '1') {
      turnOn();
    } else if(incomingValue == '0') {
      turnOff();
    }
  }
}

void turnOff() {
  setColor(0, 0, 0);
}

void turnOn() {
  int r = 0;
  int g = 0;
  int b = 0;
  int wait = 100;

  while (r < 254 && b < 254 && g < 150) {
    if (r < 253) {
      r++;
    }

    if (r % 4 == 0) {
      g++;
    }

    if (r > 200) {
      g++;
      if (g % 2 == 0) {
        b++;
      }
    }

    setColor(r, g, b);
    delay(500);
  }
 
}

void setColor(int r, int g, int b) {
  for(int i=0; i < strip.numPixels(); i++) { // For each pixel in strip...
    strip.setPixelColor(i, strip.Color(r, g, b));
    strip.show(); // Update strip with new contents
  }
}
