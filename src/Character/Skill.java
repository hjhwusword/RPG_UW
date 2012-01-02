package battle;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import util.*;

public class Skill {
	public static enum Skills{
		// warrior
		HEAVY_BLOW,
		CRANIUM_BLOW,
		HULK_BLOW,
		
		WIDE_SLASH,
		SWEEPING_SLASH,
		HURRICANE_SWIPE,
		
		// beserker
		RAGE,
		WHIRLWIND,
		PSYCHOTIC_FURY,
		
		// titan
		DEFLECT,
		CHARGING_STAR,
		SECRET_GUARD,
		
		// rogue
		DUEL_STRIKE,
		TRIPLE_STRIKE,
		QUINT_STRIKE,
		
		PIN_POINT_STRIKE,
		DEHABILITATE,
		FATALITY,
		
		// assassin
		BACK_STAB,
		DEATH_FLASH,
		REAPER,
		
		// archer
		PIERCING_ARROW,
		MASSIVE_DESTRUCTION,
		HEAD_SHOT,
		
		// mage
		MAGIC_BOLT,
		GREATER_MAGIC_BOLT,
		GINORMAOUS_MAGIC_BOLT,
		
		HEALING,
		GREATER_HEALING,
		CLEANSE,
		
		// elementalist
		FIRE_BALL,
		FIRE_WAVE,
		FIRE_EXPLOSDOM,
		
		ICE_BALL,
		ICE_WAVE,
		ICE_EXPLOSDOM,
		
		LIGHTNING_BALL,
		LIGHTNING_WAVE,
		LIGHTNING_EXPLOSDOM,
		
		// healer
		SPDUP,
		DEFUP,
		ATKUP,
		
		SPDDOWN,
		DEFDOWN,
		ATKDOWN,
		
		HEAL_ALL,
		CLEANSE_ALL,
		REVIVE
	}
	
	public static enum Status {
		FREEZE,
		POISON,
		BURN,
		PARALYZE,
		STUN,
		
		ATKUP,
		DEFUP,
		SPDUP,
		
		ATKDOWN,
		DEFDOWN,
		SPDDOWN,
		
		ALL_CLEAR,
		NON
	}
	
	public static enum Area {
		SQUARE, // k
		ROW,
		COL, 
		SINGLE, // k
		ALL,    // k
		R,
		RTHREE,
		RARROW,
		RROW
		
	}
		
	protected Skills sk;
	
	protected Status status;
	protected Area area;
	protected Stats stat; 
	private List<Animation> animation;
	private BattleAction ba;
	
	private Random r;
	//private List<Integer> targetExpand;
	
	public Skill(Skills input) {
		sk = input;
		animation = new ArrayList<Animation>();
	//	this.ba = null;
		status = Skill.Status.NON;
		r = new Random();
		area = Area.SINGLE;
		//targetExpand = new ArrayList<Integer>();
		convertSkill(input);
		
	}
	
	
	public void setBattleAction(BattleAction ba) {
		this.ba = ba;
	}
	
	private void convertSkill(Skills input) {
		Animation anime = null;
		Animation anime2 = null;
		switch(input) {
		// elementalist ////////////////////////////////////////////////////
		case FIRE_BALL:
			anime = new Animation(new SeriesImageLoader("fire_3.png", 5, 5));
			anime.setPoint(-80, -70);
			stat = new Stats(30);
			chance(40, Skill.Status.BURN);
			
			break;
		case FIRE_WAVE:
			anime = new Animation(new SeriesImageLoader("fire_2.png", 5, 5));
			anime.setPoint(-80, -100);
			stat = new Stats(50);
			
			animateAll(anime, -80, -100, 50, 80, 2);
			
			chance(30, Skill.Status.BURN);
			area = Area.SQUARE;
			break;
		case FIRE_EXPLOSDOM:
			anime = new Animation(new SeriesImageLoader("fire_1.png", 5, 8));
			anime.setPoint(-80, -70);
			stat = new Stats(60);
			
			animateAll(anime, -80, -70, 50, 80, 3);
			
			chance(20, Skill.Status.BURN);
			area = Area.ALL;
			break;
		
		case ICE_BALL:
			anime = new Animation(new SeriesImageLoader("ice_5.png", 5, 5));
			anime.setPoint(-40, -40);
			
			stat = new Stats(20);
			chance(30, Skill.Status.FREEZE);
			
			break;
		case ICE_WAVE:
			anime = new Animation(new SeriesImageLoader("ice_3.png", 5, 5));
			anime.setPoint(-30, -20);
			stat = new Stats(40);
			
			animateAll(anime, -30, -20, 50, 80, 2);
			
			chance(25, Skill.Status.FREEZE);
			area = Area.SQUARE;
			break;
		case ICE_EXPLOSDOM:
			anime = new Animation(new SeriesImageLoader("ice_4.png", 5, 5));
			anime.setPoint(-30, -40);
			stat = new Stats(50);
			
			animateAll(anime, -30, -40, 55, 80, 3);
			
			chance(20, Skill.Status.FREEZE);
			area = Area.ALL;
			break;
			
		case LIGHTNING_BALL:
			anime = new Animation(new SeriesImageLoader("thunder_3.png", 5, 4));
			anime.setPoint(-75, -115);
			stat = new Stats(30);
			chance(20, Skill.Status.PARALYZE);
			break;
			
		case LIGHTNING_WAVE:
			anime = new Animation(new SeriesImageLoader("thunder_2.png", 5, 4));
			stat = new Stats(50);
			anime.setPoint(-80, -130);
			
			animateAll(anime, -80, -130, 50, 80, 2);
			
			chance(15, Skill.Status.PARALYZE);
			area = Area.SQUARE;
			break;
			
		case LIGHTNING_EXPLOSDOM:
			anime = new Animation(new SeriesImageLoader("thunder_1.png", 5, 6 ));
			stat = new Stats(60);
			chance(10, Skill.Status.PARALYZE);
			area = Area.ALL;
			break;
			
		// rogue ///////////////////////////////////////////////////////////////////
		case DUEL_STRIKE:
			anime = new Animation(new SeriesImageLoader("swordman_1.png", 4, 11 ));
			stat = new Stats(30);
			anime.setPoint(-5, -20);
			int[] temp = {14, 15, 25, 26, 27, 15, 25, 26, 27};
			
			anime.save(temp);
			anime.delay(2);
			break;
		case TRIPLE_STRIKE:
			anime = new Animation(new SeriesImageLoader("swordman_1.png", 4, 11, true));
			stat = new Stats(50);
			//anime.save(new int[] {14, 15, 25, 26, 27, 15, 25, 26, 27});
			anime.save(new int[] {19, 20, 15, 25, 27, 19, 24, 29, 30, 14, 15, 25, 26, 27});
			anime.delay(2);
			anime.setPoint(-70, -20);
			break;
		case QUINT_STRIKE:
			//anime = new Animation(new SeriesImageLoader("swordman_1.png", 4, 11));
			anime = new Animation(new SeriesImageLoader("swordman_1.png", 4, 11 ));
			stat = new Stats(70);
			anime.setPoint(-5, -20);
			
			anime.save(new int[] {14, 15, 25, 26, 27, 14, 15, 25, 26, 27, -1, -1, -14, -15, -25, -26, -27, -15, -25, -26, -1, -1, 18, 19, 14, 15, 25, 25, 26, 26, 27, 27});
			anime.delay(2);
			
			anime2 = new Animation(new SeriesImageLoader("swordman_1.png", 4, 11, true));
			anime2.save(new int[] {-14, -15, -25, -26, -27, -15, -25, -26, -27, -1, -1, 14, 15, 25, 26, 27, 14, 15, 25, 26, 27});
			anime2.delay(2);
			anime2.setPoint(-70, -20);
			animation.add(anime2);
			
			break;
		
		case PIN_POINT_STRIKE:
			anime = new Animation(new SeriesImageLoader("swordman_3.png", 5, 5));
			stat = new Stats(30);
			anime.setPoint(-10, -55);
			anime.save(new int[] {5, 2, 3, 4});
			anime.delay(3);
			break;
			
		case DEHABILITATE:
			anime = new Animation(new SeriesImageLoader("swordman_3.png", 5, 5));
			stat = new Stats(40);
			anime.setPoint(-10, -55);
			anime.save(new int[] {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
			
			anime.delay(3);
			break;
		
		case FATALITY:
			anime = new Animation(new SeriesImageLoader("swordman_3.png", 5, 5));	
			stat = new Stats(50);
			anime.setPoint(-10, -55);
			anime.delay(2);
			break;
			
		// warror //////////////////////////////////////////////////////////////////////////
		case HEAVY_BLOW:
			anime = new Animation(new SeriesImageLoader("swordman_4_fix.png", 5, 5, true));
			stat = new Stats(40);
			anime.save(new int[] {19, 18, 17, 16, 15});
			anime.delay(2);
			anime.setPoint(-60, -25);
			break;
		
		case CRANIUM_BLOW:
			anime = new Animation(new SeriesImageLoader("swordman_4_fix.png", 5, 5, true));
			stat = new Stats(50);
			anime.save(new int[] {19, 18, 17, 16, 15, 0, 1, 2, 9, 14, 13, 12});
			anime.delay(2);
			anime.setPoint(-60, -25);
			break;
		
		case HULK_BLOW:
			anime = new Animation(new SeriesImageLoader("swordman_4_fix.png", 5, 5, true));
			stat = new Stats(60);
		//	anime.save(new int[] {19, 18, 17, 16, 15, 0, 1, 2, 9, 14, 13, 12, 19, 18, 17, 16, 15});
			anime.save(new int[] {19, 18, 17, 16, 15});
			anime.delay(3);
			anime.setPoint(-60, -25);
			
			anime2 = new Animation(new SeriesImageLoader("slash_3.png", 5, 5 ));
			anime2.setPoint(-30, -35);
			anime2.shift(0, 5);
			animation.add(anime2);
			
			break;
		
		case WIDE_SLASH:
			anime = new Animation(new SeriesImageLoader("swordman_2.png", 5, 5, true));
			stat = new Stats(30);
			anime.setPoint(-80, -60);
			anime.save(new int[] {5, 6, 7, 8, 9, 10, 11});
			anime.delay(2);
		
			anime2 = anime.share();
			anime2.setPoint(-80, 30);
			animation.add(anime2);
			
			anime2 = anime.share();
			anime2.setPoint(-80, 120);
			animation.add(anime2);
			
			
			area = Area.COL;
			break;
		
		case SWEEPING_SLASH:
			anime = new Animation(new SeriesImageLoader("swordman_2.png", 5, 5, true));
			stat = new Stats(40);
			anime.setPoint(-80, -60);
			anime.save(new int[] {5, 6, 7, 8, 9, 10, 11, 15, 19, 20, 21, 22, 23});
			anime.delay(2);
		
			anime2 = anime.share();
			anime2.setPoint(-80, 30);
			animation.add(anime2);
			
			anime2 = anime.share();
			anime2.setPoint(-80, 120);
			animation.add(anime2);
			area = Area.COL;
			break;
		
		case HURRICANE_SWIPE:
			anime = new Animation(new SeriesImageLoader("swordman_2.png", 5, 5, true));
			stat = new Stats(40);
			anime.setPoint(-80, -60);
			anime.save(new int[] {5, 6, 7, 8, 9, 10, 11, 15, 19, 20, 21, 22, 23});
			anime.delay(2);
			//anime.extend(10);
			
			anime2 = new Animation(new SeriesImageLoader("wind_2.png", 5, 6));
			anime2.setPoint(-80, -80);
			anime2.shift(0, 26);
			animation.add(anime2);
			
			anime2 = anime.share();
			anime2.setPoint(-80, 30);
			//anime2.extend(10);
			animation.add(anime2);
			
			anime2 = new Animation(new SeriesImageLoader("wind_2.png", 5, 6));
			anime2.setPoint(-80, 10);
			anime2.shift(0, 26);
			animation.add(anime2);
			
			anime2 = anime.share();
			anime2.setPoint(-80, 120);
			//anime2.extend(10);
			animation.add(anime2);
			
			anime2 = new Animation(new SeriesImageLoader("wind_2.png", 5, 6));
			anime2.setPoint(-80, 100);
			anime2.shift(0, 26);
			animation.add(anime2);
			area = Area.COL;
			break;
			
		// mage //////////////////////////////////////////////////////////////////
			
		case MAGIC_BOLT:
			anime = new Animation(new SeriesImageLoader("light_1.png", 5, 5));
			stat = new Stats(20);
			anime.setPoint(-80, -80);
			break;
		
		case GREATER_MAGIC_BOLT:
			anime = new Animation(new SeriesImageLoader("light_2.png", 5, 6));
			stat = new Stats(30);
			anime.setPoint(-80, -80);
			break;
		
		case GINORMAOUS_MAGIC_BOLT:
			anime = new Animation(new SeriesImageLoader("light_3.png", 5, 7));
			stat = new Stats(40);
			anime.setPoint(-80, -110);
			
			anime2 = new Animation(new SeriesImageLoader("light_4.png", 5, 4));
			anime2.setPoint(-35, -35);
			anime2.shift(0, 35);
			animation.add(anime2);
			break;
		
		case HEALING:
			anime = new Animation(new SeriesImageLoader("heal_4.png", 5, 5));
			stat = new Stats(-30);
			anime.setPoint(-30, -40);
			break;
		
		case GREATER_HEALING:
			anime = new Animation(new SeriesImageLoader("heal_5.png", 5, 5));
			stat = new Stats(-50);
			anime.setPoint(-30, -40);
			break;
		
		case CLEANSE:
			anime = new Animation(new SeriesImageLoader("cast_4.png", 5, 7));
			stat = new Stats(-30);
			anime.setPoint(-80, -80);
			status = Skill.Status.ALL_CLEAR;
			break;
		
		// assasin //////////////////////////////////////////////////////////
		case BACK_STAB:
			anime = new Animation(new SeriesImageLoader("assasin_1.png", 3, 8));
			stat = new Stats(50);
			anime.setPoint(10, -10);
			anime.save(new int[] {18, 19, 20, 3, 4, 5, 6, 7, 8, 0, 1, 2});
			break;
		
		case DEATH_FLASH:
			anime = new Animation(new SeriesImageLoader("assasin_1.png", 3, 8, true));
			stat = new Stats(60);
			anime.save(new int[] {3, 4, 5, 12, 13, 14, 18, 19, 20});
			anime.setPoint(-30, -10);
			
			anime2 = new Animation(new SeriesImageLoader("assasin_1.png", 3, 8));
			anime2.save(new int[] {18, 19, 20, 0, 1, 2, 6, 7, 8, 0, 1, 2, 18, 19, 20});
			anime2.setPoint(70, -10);
			anime2.shift(0, 9);
			animation.add(anime2);
			
			anime2 = anime2.shareLess();
			anime2.setPoint(10, 80);
			anime2.shift(0, 15);
			animation.add(anime2);
			
			anime2 = anime2.shareLess();
			anime2.setPoint(10, -10);
			anime2.shift(0, 15);
			animation.add(anime2);
			
			anime2 = anime2.shareLess();
			anime2.setPoint(70, 80);
			anime2.shift(0, 15);
			animation.add(anime2);
			
			area = Area.SQUARE;
			break;
		
		case REAPER:
			anime = new Animation(new SeriesImageLoader("assasin_1.png", 3, 8));
			if(chance(50)) {
				stat = new Stats(999);
			} else {
				stat = new Stats(0);
			}
			anime.save(new int[] {3, 4, 5, 0, 1, 2, 15, 16, 17, 12, 13, 14, 6, 7, 8});
			anime.setPoint(10, -10);
			
			anime2 = new Animation(new SeriesImageLoader("dark_0.png", 5, 5));
			anime2.setPoint(-30, -40);
			anime2.shift(0, 20);
			animation.add(anime2);
			break;
			
		// neo ///////////////////////////////////////////////////////////////////////////////
		case SPDUP:
			anime = new Animation(new SeriesImageLoader("cast_3.png",5, 7));
			stat = new Stats(0);
			anime.setPoint(-80, -80);
			status = Skill.Status.SPDUP;
			break;
		case ATKUP:
			anime = new Animation(new SeriesImageLoader("cast_1.png", 5, 4));
			stat = new Stats(0);
			anime.setPoint(-80, -80);
			status = Skill.Status.ATKUP;
			break;
		case DEFUP:
			anime = new Animation(new SeriesImageLoader("earth_1.png", 5, 4));
			stat = new Stats(0);
			anime.setPoint(-80, -120);
			status = Skill.Status.DEFUP;
			break;
		
		case SPDDOWN:
			anime = new Animation(new SeriesImageLoader("dark_1.png", 5, 5));
			stat = new Stats(0);
			anime.setPoint(-30, -30);
			status = Skill.Status.SPDDOWN;
			break;
		
		case ATKDOWN:
			anime = new Animation(new SeriesImageLoader("cast_2.png", 5, 4));
			stat = new Stats(0);
			anime.setPoint(-80, -80);
			status = Skill.Status.ATKDOWN;
			break;
		
		case DEFDOWN:
			anime = new Animation(new SeriesImageLoader("ice_1.png", 5, 7));
			stat = new Stats(0);
			anime.setPoint(-80, -80);
			status = Skill.Status.DEFDOWN;
			break;
		
		case HEAL_ALL:
			anime = new Animation(new SeriesImageLoader("heal_7.png", 5, 5));
			stat = new Stats(-80);
			anime.setPoint(-30, -40);
			
			animateAll(anime, -30, -40, 50, 80, 3);
			
			area = Area.ALL;
			break;
			
		case CLEANSE_ALL:
			anime = new Animation(new SeriesImageLoader("heal_6.png", 5, 5));
			stat = new Stats(-40);
			anime.setPoint(-10, -50);
			
			animateAll(anime, -10, -50, 50, 80, 3);
			
			status = Skill.Status.ALL_CLEAR;
			area = Area.ALL;
			break;
		
		case REVIVE:
			anime = new Animation(new SeriesImageLoader("light_5.png", 5, 5));
			stat = new Stats(-100);
			anime.setPoint(-30, -30);
			break;
			
		// archer ///////////////////////////////////////////////////////////////////
		case PIERCING_ARROW:
			anime = new Animation2(new SeriesImageLoader("arrow.png", 4, 4), new Point(-100, 0), new Point(10, 0));
			stat = new Stats(30);
			anime.setPoint(0, -10);
			anime.save(new int[] {4});
			break;
		
		case MASSIVE_DESTRUCTION:
			anime = new Animation2(new SeriesImageLoader("arrow.png", 4, 4), new Point(-100, 0), new Point(10, 0));
			stat = new Stats(40);
			anime.setPoint(0, -10);
			anime.save(new int[] {8});
			anime.shift(-1, 5);
			
			anime2 = new Animation2(new SeriesImageLoader("arrow.png", 4, 4), new Point(-100, 0), new Point(10, 0));
			anime2.setPoint(0, -20);
			anime2.save(new int[] {8});
			anime2.shift(-1, 55);
			animation.add(anime2);
			
			anime2 = new Animation2(new SeriesImageLoader("arrow.png", 4, 4), new Point(-100, 0), new Point(10, 0));
			anime2.setPoint(0, -10);
			anime2.save(new int[] {8});
			anime2.shift(-1, 110);
			animation.add(anime2);
			break;
			
		case HEAD_SHOT:
			anime = new Animation2(new SeriesImageLoader("arrow.png", 4, 4), new Point(-100, 0), new Point(10, 0));
			stat = new Stats(50, 50, 0, 0, 0, 0, 0);
			anime.setPoint(0, -20);
			anime.save(new int[] {8});
			
			anime2 = new Animation(new SeriesImageLoader("posion_1.png", 5, 6));
			anime2.setPoint(-80, -80);
			anime2.shift(0, 10);
			animation.add(anime2);
			
			break;
			
		// BESERKER //////////////////////////////////////////
			
			
			
		}
		
		animation.add(anime);
		//stat = new Stats(50);
	}
	
	
	public Stats getEffect() {
		return stat;
	}
	
	public Skill.Status getStatus() {
		return status;
	}
	
	public List<Animation> getAnimation() {
		return animation;
	}
	
	public Skill.Area getArea() {
		return area;
	}
	
	/*
	public List<Integer> getTargetExpand() {
		return targetExpand;
	}*/
	
	public void chance(int percent, Skill.Status st) {
		if(chance(percent))
			status = st;
	}
	
	public boolean chance(int percent) {
		return r.nextInt(101) < percent;
			
	}
	
	public void drawAnimation(Graphics g, int x, int y) {
	
	}
	
	public void setPoint(int x, int y) {
		//for(int i = 0; )
		for(int i = 0; i < animation.size(); i++) {
			animation.get(i).setPoint(x, y);
		}
	}
	
	
	
	public String toString() {
		return sk.toString();
	}
	
	public void animateAll(Animation anime,int x, int y, int dx, int dy, int size) {
		Animation temp;
		helpAnimateAll(anime, x, y, dx, size);
		for(int i = 1; i < size; i++) {
			temp = anime.share();
			temp.setPoint(x, y + i * dy);
			animation.add(temp);
			helpAnimateAll(temp, x, y + i * dy, dx, size);
		}
	}
	
	public void helpAnimateAll(Animation anime, int x, int y, int dx, int size) {
		Animation temp;
		for(int i = 1; i < size; i++) {
			temp = anime.share();
			temp.setPoint(i * dx + x, y);
			animation.add(temp);
		}
	}
	
	
	
	
	
}
