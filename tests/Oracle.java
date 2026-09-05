import com.google.gson.*;
import java.awt.image.BufferedImage;
import java.applet.AudioClip;
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import javax.imageio.ImageIO;

// Executes the actual archived bytecode, not a recompiled decompiler output.
public class Oracle {
    static Gson gson = new Gson();
    static JsonObject schema;
    static invader game;
    static List<Integer> sounds = new ArrayList<>();
    static String name(String value) {
        return value.length() == 4 ? Character.toString((char)Integer.parseInt(value, 16)) : value;
    }
    static Field field(Object target, String id) throws Exception {
        Field f = target.getClass().getDeclaredField(name(id)); f.setAccessible(true); return f;
    }
    static Object get(Object target, String id) throws Exception { return field(target, id).get(target); }
    static void set(Object target, String id, Object value) throws Exception { field(target, id).set(target, value); }
    static Object snapshot(Object target, JsonObject map) throws Exception {
        Map<String, Object> result = new LinkedHashMap<>();
        for (var entry : map.entrySet()) {
            JsonElement spec = entry.getValue();
            if (spec.isJsonPrimitive()) { result.put(entry.getKey(), get(target, spec.getAsString())); continue; }
            JsonObject desc = spec.getAsJsonObject();
            if (desc.has("fields")) result.put(entry.getKey(), snapshot(get(target, desc.get("field").getAsString()), desc.getAsJsonObject("fields")));
            else {
                List<Object> values = new ArrayList<>();
                if (desc.has("slots")) for (JsonElement slot : desc.getAsJsonArray("slots")) values.add(snapshot(get(target, slot.getAsString()), desc.getAsJsonObject("items")));
                else for (Object item : (Object[])get(target, desc.get("field").getAsString())) values.add(snapshot(item, desc.getAsJsonObject("items")));
                result.put(entry.getKey(), values);
            }
        }
        return result;
    }
    static void patch(Object target, JsonObject map, JsonObject values) throws Exception {
        for (var entry : values.entrySet()) {
            JsonElement spec = map.get(entry.getKey()), value = entry.getValue();
            if (spec.isJsonPrimitive()) {
                Field f = field(target, spec.getAsString());
                f.set(target, gson.fromJson(value, f.getType()));
            } else {
                JsonObject desc = spec.getAsJsonObject();
                if (desc.has("fields")) patch(get(target, desc.get("field").getAsString()), desc.getAsJsonObject("fields"), value.getAsJsonObject());
                else {
                    Object[] items;
                    if (desc.has("slots")) {
                        items = new Object[desc.getAsJsonArray("slots").size()];
                        for (int i=0;i<items.length;i++) items[i]=get(target,desc.getAsJsonArray("slots").get(i).getAsString());
                    } else items = (Object[])get(target, desc.get("field").getAsString());
                    for (int i=0;i<value.getAsJsonArray().size();i++) patch(items[i],desc.getAsJsonObject("items"),value.getAsJsonArray().get(i).getAsJsonObject());
                }
            }
        }
    }
    static void invoke(String id, JsonArray args) throws Exception {
        Class<?>[] types = new Class<?>[args.size()]; Object[] vals = new Object[args.size()];
        for (int i=0;i<args.size();i++) { types[i]=int.class; vals[i]=args.get(i).getAsInt(); }
        game.getClass().getMethod(name(id),types).invoke(game,vals);
    }
    static void init(long seed) throws Exception {
        Field random = Class.forName("java.lang.Math$RandomNumberGeneratorHolder").getDeclaredField("randomNumberGenerator");
        random.setAccessible(true); ((Random)random.get(null)).setSeed(seed);
        game = new invader();
        game.setSize(400,350);
        for (int i=0;i<20;i++) { game.\u00e4[i]=new d(); game.\u00e5[i]=new d(); game.\u00e7[i]=new m(); }
        for (int i=0;i<5;i++) game.\u00e6[i]=new k();
        game.\u010d = new boolean[9];
        game.\u0114 = new BufferedImage(400,350,BufferedImage.TYPE_INT_ARGB).getGraphics();
        game.\u0120 = new a(400,350,250,25); game.\u0120.M=false;
        game.\u0135 = new AudioClip[14];
        for (int i : new int[]{0,1,4,5,7,8,9,10}) {
            final int id=i;
            game.\u0135[i]=new AudioClip(){ public void play(){sounds.add(id);} public void stop(){} public void loop(){} };
        }
        String base="reference/AlienInvasion/";
        game.\u0115=new java.awt.Image[17];
        for(int i=0;i<17;i++) game.\u0115[i]=ImageIO.read(new File(base+String.format("exp/exp-%02d.gif",i+1)));
        game.\u0116=new java.awt.Image[4]; game.\u0117=new java.awt.Image[4]; game.\u0118=new java.awt.Image[4]; game.\u0119=new java.awt.Image[4];
        for(int i=0;i<4;i++) {
            game.\u0116[i]=ImageIO.read(new File(base+"gfx/ALIEN-1"+(char)('A'+i)+".GIF"));
            game.\u0117[i]=ImageIO.read(new File(base+"gfx/ALIEN-3"+(char)('A'+i)+".GIF"));
            game.\u0118[i]=ImageIO.read(new File(base+"gfx/ALIEN-2"+(char)('A'+i)+".GIF"));
            game.\u0119[i]=ImageIO.read(new File(base+"gfx/n_tp"+((i+1)*2)+".gif"));
        }
        game.\u011a=ImageIO.read(new File(base+"gfx/n_cluster.gif"));
        game.\u011b=ImageIO.read(new File(base+"gfx/n_missile.gif"));
        game.\u011c=ImageIO.read(new File(base+"gfx/n_player.gif"));
        game.\u011d=ImageIO.read(new File(base+"gfx/earth.gif"));
        game.\u011e=ImageIO.read(new File(base+"gfx/pickup.gif"));
        game.\u011f=ImageIO.read(new File(base+"gfx/n_player_half.gif"));
        game.\u00c0(); game.\u00cf(); game.\u00ce();
    }
    public static void main(String[] args) throws Exception {
        schema=JsonParser.parseString(Files.readString(Path.of("tests/state-map.json"))).getAsJsonObject();
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line=in.readLine())!=null) {
            JsonObject cmd=JsonParser.parseString(line).getAsJsonObject(); sounds.clear();
            if(cmd.has("seed")) { if(game!=null) game.\u0120.dispose(); init(cmd.get("seed").getAsLong()); }
            if(cmd.has("patch")) patch(game,schema,cmd.getAsJsonObject("patch"));
            if(cmd.has("method")) invoke(cmd.get("method").getAsString(),cmd.has("args")?cmd.getAsJsonArray("args"):new JsonArray());
            if(cmd.has("down")) game.keyDown(null,cmd.get("down").getAsInt());
            if(cmd.has("up")) game.keyUp(null,cmd.get("up").getAsInt());
            if(cmd.has("frames")) for(int i=0;i<cmd.get("frames").getAsInt();i++) { game.\u00d1(); game.n(game.\u0114); }
            if(cmd.has("screenshot")) {
                BufferedImage img=new BufferedImage(400,350,BufferedImage.TYPE_INT_ARGB);
                game.\u0114=img.getGraphics(); game.n(game.\u0114);
                ImageIO.write(img,"png",new File(cmd.get("screenshot").getAsString()));
            }
            Map<String,Object> output=new LinkedHashMap<>(); output.put("state",snapshot(game,schema)); output.put("sounds",sounds);
            System.out.println(gson.toJson(output));
        }
        if(game!=null) game.\u0120.dispose();
        System.exit(0);
    }
}
