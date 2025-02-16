[1mdiff --git a/lwjgl3/src/main/java/com/myg2x/game/lwjgl3/GridScreen.java b/lwjgl3/src/main/java/com/myg2x/game/lwjgl3/GridScreen.java[m
[1mindex 7e501f3..3ffabbf 100644[m
[1m--- a/lwjgl3/src/main/java/com/myg2x/game/lwjgl3/GridScreen.java[m
[1m+++ b/lwjgl3/src/main/java/com/myg2x/game/lwjgl3/GridScreen.java[m
[36m@@ -24,32 +24,32 @@[m [mimport com.badlogic.gdx.physics.box2d.World;[m
 [m
 public class GridScreen extends Scene{[m
 	private Random rand;[m
[31m-	[m
[32m+[m
 	//World viewport[m
 	private FitViewport viewport;[m
[31m-	[m
[32m+[m
 	//Sprite batch and shape renderer[m
 	private SpriteBatch batch;[m
 	private ShapeRenderer shape;[m
[31m-	[m
[32m+[m
 	//EntityManager for entityList[m
 	private EntityManager entityManager;[m
[31m-	[m
[32m+[m
 	//Textures[m
 	private Texture bucketImage;[m
 	private Texture dropImage;[m
[31m-	[m
[32m+[m
 	private Player player;[m
 	private TextureObject enemy;[m
[31m-	[m
[31m-	[m
[32m+[m
[32m+[m
 	private Grid grid;[m
 	private Music music;[m
[31m-	[m
[32m+[m
 	public GridScreen(final AbstractEngine game)[m
 	{[m
 		entityManager = new EntityManager();[m
[31m-		[m
[32m+[m
 		music = Gdx.audio.newMusic(Gdx.files.internal("16. Thorn in You (Calm_Roar) _【Fire Emblem Fates OST_ Map Themes Mixed】 【HQ 1080p】.mp3"));[m
 		music.play();[m
 		music.setVolume(0.4f);[m
[36m@@ -57,27 +57,27 @@[m [mpublic class GridScreen extends Scene{[m
 		// grid initialization[m
 		grid = new Grid();[m
 [m
[31m-		[m
[32m+[m
 		rand = new Random();[m
[31m-		[m
[32m+[m
 		batch = new SpriteBatch();[m
 		shape = new ShapeRenderer();[m
 		viewport = new FitViewport(8, 5);[m
[31m-		[m
[32m+[m
 		dropImage = new Texture(Gdx.files.internal("Square.png"));[m
[31m-		[m
[32m+[m
 		bucketImage = new Texture(Gdx.files.internal("Circle.png"));[m
[31m-		[m
[32m+[m
 		player = new Player(grid.getOffset(), grid.getOffset(), 4.f, bucketImage);[m
[31m-		enemy = new TextureObject(grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset(), [m
[31m-									grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset(), rand.nextFloat(2.f), dropImage);[m
[31m-		[m
[32m+[m		[32menemy = new TextureObject(grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset(),[m
[32m+[m									[32mgrid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset(), rand.nextFloat()*2.f, dropImage);[m
[32m+[m
 		entityManager.addEntity(player);[m
[31m-		[m
[32m+[m
 		entityManager.addEntity(enemy);[m
[31m-		[m
[32m+[m
 //		dropletList = new Array<TextureObject>();[m
[31m-//		[m
[32m+[m[32m//[m
 //		for(int i = 0; i < 1; i++)[m
 //		{[m
 //			TextureObject droplet = new TextureObject(rand.nextFloat(8), 4, rand.nextFloat(2.f), dropImage);[m
[36m@@ -85,13 +85,13 @@[m [mpublic class GridScreen extends Scene{[m
 //			entityManager.addEntity(droplet);[m
 //		}[m
 	}[m
[31m-	[m
[31m-	[m
[32m+[m
[32m+[m
 	public void resize(int width, int height)[m
 	{[m
 		viewport.update(width, height, true);[m
 	}[m
[31m-	[m
[32m+[m
 	//Functions as update as well[m
 	@Override[m
 	public void render(float delta)[m
[36m@@ -100,23 +100,23 @@[m [mpublic class GridScreen extends Scene{[m
 		input();[m
 		logic();[m
 	}[m
[31m-	[m
[32m+[m
 	//Any drawing / rendering put into here[m
 	public void draw()[m
 	{[m
 		//Background[m
 		ScreenUtils.clear(0, 0, 0.2f, 1);[m
[31m-		[m
[32m+[m
 		//Viewport stuff[m
 		viewport.apply();[m
 		batch.setProjectionMatrix(viewport.getCamera().combined);[m
[31m-		[m
[32m+[m
 		//SpriteBatch drawing[m
 		batch.begin();[m
 			//Render entities[m
 			entityManager.render(batch);[m
 		batch.end();[m
[31m-		[m
[32m+[m
 		shape.setProjectionMatrix(viewport.getCamera().combined);[m
 [m
 [m
[36m@@ -124,30 +124,30 @@[m [mpublic class GridScreen extends Scene{[m
 		//Draw rect / hit box outline of entities[m
 		shape.begin(ShapeType.Line);[m
 			shape.setColor(Color.RED);[m
[31m-			shape.rect(player.getRect().getX(), player.getRect().getY(), [m
[32m+[m			[32mshape.rect(player.getRect().getX(), player.getRect().getY(),[m
 					player.getRect().getWidth(), player.getRect().getHeight());[m
[31m-			[m
[31m-			shape.rect(enemy.getRect().getX(), enemy.getRect().getY(), [m
[32m+[m
[32m+[m			[32mshape.rect(enemy.getRect().getX(), enemy.getRect().getY(),[m
 					enemy.getRect().getWidth(), enemy.getRect().getHeight());[m
[31m-		[m
[31m-		[m
[32m+[m
[32m+[m
 		shape.setColor(Color.WHITE);[m
 		for (int row = 0; row < grid.getHeight() - 1; row++) {[m
 		    for (int col = 0; col < grid.getWidth() - 1; col++) {[m
 [m
[31m-		        shape.rect(col * grid.getTileSize() + grid.getOffset(), [m
[32m+[m		[32m        shape.rect(col * grid.getTileSize() + grid.getOffset(),[m
 		        			row * grid.getTileSize() + grid.getOffset(), 1, 1);[m
 		    }[m
 		}[m
 //			for(TextureObject droplet : dropletList)[m
 //			{[m
[31m-//				shape.rect(droplet.getRect().getX(), droplet.getRect().getY(), [m
[32m+[m[32m//				shape.rect(droplet.getRect().getX(), droplet.getRect().getY(),[m
 //						droplet.getRect().getWidth(), droplet.getRect().getHeight());[m
 //			}[m
[31m-			[m
[32m+[m
 		shape.end();[m
 	}[m
[31m-	[m
[32m+[m
 	//Program logic put here, sort of the update() function that will be called in render()[m
 	public void logic()[m
 	{[m
[36m@@ -155,32 +155,32 @@[m [mpublic class GridScreen extends Scene{[m
 		//System.out.printf("Player position: %.2f, %.2f\n", player.getRect().x, player.getRect().y);[m
 		//System.out.printf("EntityList Size: %d\n", entityManager.getListSize());[m
 		//System.out.printf("List size %d\n", dropletList.length);[m
[31m-	    [m
[32m+[m
 		float worldWidth = viewport.getWorldWidth();[m
[31m-	    float worldHeight = viewport.getWorldHeight();		[m
[31m-		[m
[32m+[m	[32m    float worldHeight = viewport.getWorldHeight();[m
[32m+[m
 		//Clamp player within viewport[m
 	    player.setPosX(MathUtils.clamp(player.getPosX(), 0, worldWidth - 0.5f));[m
 		player.setPosY(MathUtils.clamp(player.getPosY(), 0, worldHeight - 0.5f));[m
[31m-		[m
[32m+[m
 		//Update function, currently not implemented[m
 		entityManager.update();[m
[31m-		[m
[31m-		[m
[31m-		[m
[32m+[m
[32m+[m
[32m+[m
 		if(enemy.getRect().overlaps(player.getRect()))[m
 		{[m
 			System.out.println("Collided");[m
 			enemy.setPosX(grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset());[m
 			enemy.setPosY(grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset());[m
[31m-			[m
[32m+[m
 			System.out.println(enemy.getPosX());[m
 			System.out.println(enemy.getPosY());[m
[31m-			[m
[31m-		[m
[32m+[m
[32m+[m
 		}[m
[31m-		[m
[31m-		[m
[32m+[m
[32m+[m
 		//Droplet falling logic[m
 //		for(TextureObject droplet : dropletList)[m
 //		{[m
[36m@@ -189,25 +189,25 @@[m [mpublic class GridScreen extends Scene{[m
 //			{[m
 //				droplet.setPosY(4f);[m
 //			}[m
[31m-//			[m
[32m+[m[32m//[m
 //			if(droplet.getRect().overlaps(player.getRect()))[m
 //			{[m
 //				System.out.println("Collided");[m
 //				entityManager.removeEntity(droplet);[m
 //				dropletList.removeValue(droplet, false);[m
 //				droplet = null;[m
[31m-//			[m
[32m+[m[32m//[m
 //			}[m
 //		}[m
[31m-		[m
[32m+[m
 	}[m
[31m-	[m
[32m+[m
 	public void input()[m
 	{[m
[31m-		[m
[32m+[m
 //		player.movement();[m
 		player.gridmovement(grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight(), enemy);[m
[31m-		[m
[32m+[m
 		//Screen click logic[m
 //		if (Gdx.input.isTouched()) { // If the user has clicked or tapped the screen[m
 //			 touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen[m
[36m@@ -216,14 +216,14 @@[m [mpublic class GridScreen extends Scene{[m
 //		     System.out.println(touchPos.x);[m
 //		     player.setPosY(touchPos.y);[m
 //	    }[m
[31m-		[m
[32m+[m
 	}[m
 [m
 [m
 	@Override[m
 	publ