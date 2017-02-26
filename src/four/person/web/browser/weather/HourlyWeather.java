package four.person.web.browser.weather;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HourlyWeather extends VBox{
	
	Label lbHourlyTime;
	Label lbHourlyWeather;
	Label lbHourlyDetail;
	Label lbHourlyTemp;
	Label lbHourlyRainAmount;
	Label lbHourlyHumidity;
	Label lbHourlyWind;
	Label lbHourlyClouds;
	
public HourlyWeather( String time, String icon, float id, String detail, float temp, float rain, float humidity, float wind, float clouds) {
		
		lbHourlyTime = new Label();
		lbHourlyWeather = new Label();
		lbHourlyDetail = new Label();
		lbHourlyTemp = new Label();
		lbHourlyRainAmount = new Label();
		lbHourlyHumidity = new Label();
		lbHourlyWind = new Label();
		lbHourlyClouds = new Label();
	
		lbHourlyTime.setPrefSize(80.0, 30.0);
		lbHourlyWeather.setPrefSize(80.0, 80.0);
		lbHourlyDetail.setPrefSize(80.0, 25.0);
		lbHourlyTemp.setPrefSize(80.0, 25.0);
		lbHourlyRainAmount.setPrefSize(80.0, 20.0);
		lbHourlyHumidity.setPrefSize(80.0, 20.0);
		lbHourlyWind.setPrefSize(80.0, 20.0);
		lbHourlyClouds.setPrefSize(80.0, 20.0);
		
		lbHourlyTime.setText( time );
		lbHourlyWeather.setId("imgLabel");
		lbHourlyWeather.setStyle("-fx-background-image: url('//res/weather/"+((int) id)+icon+".png');");
		lbHourlyDetail.setText( detail );
		lbHourlyTemp.setText( Math.round(temp*10)/10.0+"вк" );
		lbHourlyRainAmount.setText( rain+"зо" );
		lbHourlyHumidity.setText( humidity+"ге" );
		lbHourlyWind.setText( wind+"з╜" );
		lbHourlyClouds.setText( clouds+"ге" );
		
		lbHourlyTime.setAlignment(Pos.CENTER);
		lbHourlyWeather.setAlignment(Pos.CENTER);
		lbHourlyDetail.setAlignment(Pos.CENTER);
		lbHourlyTemp.setAlignment(Pos.CENTER);
		lbHourlyRainAmount.setAlignment(Pos.CENTER);
		lbHourlyHumidity.setAlignment(Pos.CENTER);
		lbHourlyWind.setAlignment(Pos.CENTER);
		lbHourlyClouds.setAlignment(Pos.CENTER);
		
		getChildren().add(lbHourlyTime);
		getChildren().add(lbHourlyWeather);
		getChildren().add(lbHourlyDetail);
		getChildren().add(lbHourlyTemp);
		getChildren().add(lbHourlyRainAmount);
		getChildren().add(lbHourlyHumidity);
		getChildren().add(lbHourlyWind);
		getChildren().add(lbHourlyClouds);
		
		setPrefSize(85.0, 250.0);
	
	}

}
