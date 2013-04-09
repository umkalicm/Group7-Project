package com.group7.project;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public interface BuildingCoordinates
{
	static List<Building> AllBuildings = new ArrayList<Building>();
	// Used this site to get coordinates: http://itouchmap.com/latlong.html
	
	// EITC-E1
	final LatLng E1_NORTHEAST = new LatLng(49.808600, -97.133249);
	final LatLng E1_SOUTHWEST = new LatLng(49.808032, -97.133834);
	final LatLngBounds E1_BOUNDS = new LatLngBounds(E1_SOUTHWEST, E1_NORTHEAST);
	static Building E1 = new Building("EITC-E1", E1_BOUNDS, "Founded: 1943, E1 has libraries, study areas and various engineering departments");

	// EITC-E2
	final LatLng E2_NORTHEAST = new LatLng(49.80895, -97.133426);
	final LatLng E2_SOUTHWEST = new LatLng(49.808399, -97.134247);
	final LatLngBounds E2_BOUNDS = new LatLngBounds(E2_SOUTHWEST, E2_NORTHEAST);
	static Building E2 = new Building("EITC-E2", E2_BOUNDS, "Founded: 2005, E2 has libraries, study areas and various engineering departments");
	
	// EITC-E3
	final LatLng E3_NORTHEAST = new LatLng(49.808652, -97.13444);
	final LatLng E3_SOUTHWEST = new LatLng(49.808116, -97.134939);
	final LatLngBounds E3_BOUNDS = new LatLngBounds(E3_SOUTHWEST, E3_NORTHEAST);
	static Building E3 = new Building("EITC-E3", E3_BOUNDS, "Founded: 1943, E3 has libraries, study areas and various engineering departments"); 
	
	// UNIVERSITY CENTRE
	final LatLng UCENTRE_NORTHEAST = new LatLng(49.809742, -97.134097);
	final LatLng UCENTRE_SOUTHWEST = new LatLng(49.808399, -97.134247);
	final LatLngBounds UCENTRE_BOUNDS = new LatLngBounds(UCENTRE_SOUTHWEST, UCENTRE_NORTHEAST);
	static Building UCENTRE = new Building("University Centre", UCENTRE_BOUNDS, "Founded: 1845, University Centre is the centre of campus. It has various facilities such as restaurants, IQ's, pool table, tax office, study areas etc.");
	
	// MY HOUSE
	final LatLng HOUSE_NORTHEAST = new LatLng(49.923167, -97.20252);
	final LatLng HOUSE_SOUTHWEST = new LatLng(49.922528, -97.203818);
	final LatLngBounds HOUSE_BOUNDS = new LatLngBounds(HOUSE_SOUTHWEST, HOUSE_NORTHEAST);
	static Building HOUSE = new Building("HOUSE", HOUSE_BOUNDS, "Founded: 1943, E3 has libraries, study areas and various engineering departments");
	
	// Drake
	final LatLng DRAKE_NORTHEAST = new LatLng(49.80839,-97.129729);
	final LatLng DRAKE_SOUTHWEST = new LatLng(49.807716,-97.130493);
	final LatLngBounds DRAKE_BOUNDS = new LatLngBounds(DRAKE_SOUTHWEST, DRAKE_NORTHEAST);
	static Building Drake = new Building("Drake", DRAKE_BOUNDS, "Founded: 1943, Drake has libraries, study areas and is the business school");

	// Fletcher Argue
	final LatLng FLETCHER_ARGUE_NORTHEAST = new LatLng(49.809942,-97.130553);
	final LatLng FLETCHER_ARGUE_SOUTHWEST = new LatLng(49.80953,-97.131288);
	final LatLngBounds FLETCHER_ARGUE_BOUNDS = new LatLngBounds(FLETCHER_ARGUE_SOUTHWEST, FLETCHER_ARGUE_NORTHEAST);
	static Building Fletcher_Argue = new Building("Fletcher Argue", FLETCHER_ARGUE_BOUNDS, "Founded: 1909, Fletcher Argue has libraries, study areas and is the arts department");

	// Elizabeth Dafoe Library
	final LatLng DAFOE_NORTHEAST = new LatLng(49.809799,-97.131544);
	final LatLng DAFOE_SOUTHWEST = new LatLng(49.809605,-97.131762);
	final LatLngBounds DAFOE_BOUNDS = new LatLngBounds(DAFOE_SOUTHWEST, DAFOE_NORTHEAST);
	static Building Elizabeth_Dafoe_Library = new Building("Elizabeth Dafoe Library", DAFOE_BOUNDS, "Founded: 1956, Elizabeth Dafoe has the biggest library on campus");

	// Helen Glass
	final LatLng GLASS_NORTHEAST = new LatLng(49.809428,-97.135184);
	final LatLng GLASS_SOUTHWEST = new LatLng(49.808809,-97.135579);
	final LatLngBounds GLASS_BOUNDS = new LatLngBounds(GLASS_SOUTHWEST, GLASS_NORTHEAST);
	static Building Helen_Glass = new Building("Helen Glass", GLASS_BOUNDS, "Founded: 1900, Hellen Glass is full of glass");

	// Parkade
	final LatLng PARKADE_NORTHEAST = new LatLng(49.809942,-97.135712);
	final LatLng PARKADE_SOUTHWEST = new LatLng(49.809171,-97.136193);
	final LatLngBounds PARKADE_BOUNDS = new LatLngBounds(PARKADE_SOUTHWEST, PARKADE_NORTHEAST);
	static Building Parkade = new Building("Parkade", PARKADE_BOUNDS, "Biggest Parkade");

	// Russell
	final LatLng RUSSELL_NORTHEAST = new LatLng(49.808341,-97.135118);
	final LatLng RUSSELL_SOUTHWEST = new LatLng(49.8078,-97.135662);
	final LatLngBounds RUSSELL_BOUNDS = new LatLngBounds(RUSSELL_SOUTHWEST, RUSSELL_NORTHEAST);
	static Building Russell = new Building("Russell", RUSSELL_BOUNDS, "Founded: 1887, Russel building was found by a guy called Russell");

	// Engineering Parking
	final LatLng ENG_PARKING_NORTHEAST = new LatLng(49.808096,-97.133314);
	final LatLng ENG_PARKING_SOUTHWEST = new LatLng(49.807807,-97.133683);
	final LatLngBounds ENG_PARKING_BOUNDS = new LatLngBounds(ENG_PARKING_SOUTHWEST, ENG_PARKING_NORTHEAST);
	static Building Eng_Parking = new Building("Engineering Parking", ENG_PARKING_BOUNDS, "Parking at engineering");

	// Buller
	final LatLng BULLER_NORTHEAST = new LatLng(49.810792,-97.133111);
	final LatLng BULLER_SOUTHWEST = new LatLng(49.810221,-97.133881);
	final LatLngBounds BULLER_BOUNDS = new LatLngBounds(BULLER_SOUTHWEST, BULLER_NORTHEAST);
	static Building Buller = new Building("Buller", BULLER_BOUNDS, "Founded: 1949, Buller has libraries, study areas and is the biology department on campus");

	// Education
	final LatLng EDUCATION_NORTHEAST = new LatLng(49.809213,-97.136659);
	final LatLng EDUCATION_SOUTHWEST = new LatLng(49.808431,-97.137255);
	final LatLngBounds EDUCATION_BOUNDS = new LatLngBounds(EDUCATION_SOUTHWEST, EDUCATION_NORTHEAST);
	static Building Education = new Building("Education", EDUCATION_BOUNDS, "Founded: 1843, EDucation has libraries, study areas and various engineering departments");

	// St. Paul's College
	final LatLng STP_NORTHEAST = new LatLng(49.810702,-97.137652);
	final LatLng STP_SOUTHWEST = new LatLng(49.809579,-97.137606);
	final LatLngBounds STP_BOUNDS = new LatLngBounds(STP_SOUTHWEST, STP_NORTHEAST);
	static Building St_Pauls_College = new Building("St. Paul's College", STP_BOUNDS, "Founded: 1908, St.Pauls has libraries, study areas and various dormitories");

	// St. John's College
	final LatLng STJ_NORTHEAST = new LatLng(49.810875,-97.136713);
	final LatLng STJ_SOUTHWEST = new LatLng(49.810259,-97.136788);
	final LatLngBounds STJ_BOUNDS = new LatLngBounds(STJ_SOUTHWEST, STJ_NORTHEAST);
	static Building St_Johns_College = new Building("St. John's College", STJ_BOUNDS, "Founded: 1908, St.Johns has libraries, study areas and various dormitories");

	// Q Lot
	final LatLng Q_NORTHEAST = new LatLng(49.812236,-97.138607);
	final LatLng Q_SOUTHWEST = new LatLng(49.810555,-97.139803);
	final LatLngBounds Q_BOUNDS = new LatLngBounds(Q_SOUTHWEST, Q_NORTHEAST);
	static Building Q_Lot = new Building("Q Lot Parking", Q_BOUNDS, "QLOT Parking");
}
