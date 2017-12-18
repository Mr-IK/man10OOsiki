package man10siki;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Man10OOsiki extends JavaPlugin {
	public static FileConfiguration config1;
	Plugin pl = Man10OOsiki.this;
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		String prefix = "§6『§aM§fa§dn§r10○○式§6』§f§l"; 
		switch(args.length) {
		 case 1:
			 if(args[0].equalsIgnoreCase("list")) {
				 if(config1.getString("siki") == null) {
					 sender.sendMessage(prefix+"§4"+"現在待機中の○○式はありません！");
					 return true;
				 }
				 for (String key : config1.getConfigurationSection("siki").getKeys(false)) {
					 sender.sendMessage(prefix+"§f"+key+": 掛け金=$"+config1.getString("siki."+ key + ".money")+" 式数="+config1.getString("siki."+ key + ".Osiki"));
					}
				 sender.sendMessage(prefix+"現在待機中○○式の一覧です。");
				 return true;
			 }
			 if(config1.getString("siki."+args[0].toString()) == null) {
				 sender.sendMessage(prefix+"§4"+"その○○式は存在しません！");
				 return true;
			 }
			 if(config1.getString("siki."+args[0].toString()+".player") == p.getName().toString()) {
				 sender.sendMessage(prefix+"§4"+"自分自身の挑戦は受けられません！");
				 return true;
			 }
				double i = Double.parseDouble(config1.getString("siki."+args[0].toString()+".money"));
				if(Vault.economy.getBalance(p)<i) {
					sender.sendMessage(prefix+"§4"+"お金が足りません！");
					return true;
				}
				if(Vault.economy.getBalance(config1.getString("siki."+args[0].toString()+".player"))<i) {
					sender.sendMessage(prefix+"§4"+"相手のお金が足りないのでこの○○式は中止します");
					 config1.set("siki."+args[0].toString(), null);
					 saveConfig();
					return true;
				}
				String im = config1.getString("siki."+args[0].toString()+".Osiki");
				int c = (int) Double.parseDouble(im);
			    Random RANDOM = new Random();
				int a = RANDOM.nextInt(c) + 1;
				Random RANDOM1 = new Random();
				int b = RANDOM1.nextInt(c) + 1;
				Bukkit.broadcastMessage(prefix+"§r§l"+sender.getName().toString()+"は"+config1.getString("siki."+args[0].toString()+".player")+"の挑戦を受けた！");
				Bukkit.broadcastMessage(prefix+"§r"+sender.getName().toString()+"はサイコロを振って"+a+"が出た！");
				Bukkit.broadcastMessage(prefix+"§r"+config1.getString("siki."+args[0].toString()+".player")+"はサイコロを振って"+b+"が出た！");
				if(a == b) {
					Bukkit.broadcastMessage(prefix+"§6"+"同じ数だったので引き分けです。");
					 config1.set("siki."+args[0].toString(), null);
					 saveConfig();
					return true;
				}
				if(a > b) {
					Bukkit.broadcastMessage(prefix+"§r§l"+sender.getName().toString()+"が勝利し$"+config1.getString("siki."+args[0].toString()+".money")+"を手に入れた！");
					String d = config1.getString("siki."+args[0].toString()+".money");
					int e = (int) Double.parseDouble(d);
					Vault.economy.depositPlayer(p, e);
					Vault.economy.withdrawPlayer(config1.getString("siki."+args[0].toString()+".player"), e);
					 config1.set("siki."+args[0].toString(), null);
					 saveConfig();
					 return true;
				}
				if(a < b) {
					Bukkit.broadcastMessage(prefix+"§r§l"+config1.getString("siki."+args[0].toString()+".player")+"が勝利し$"+config1.getString("siki."+args[0].toString()+".money")+"を手に入れた！");
					String d = config1.getString("siki."+args[0].toString()+".money");
					int e = (int) Double.parseDouble(d);
					Vault.economy.depositPlayer(config1.getString("siki."+args[0].toString()+".player"), e);
					Vault.economy.withdrawPlayer(p, e);
					 config1.set("siki."+args[0].toString(), null);
					 saveConfig();
					 return true;
				}
				
			 break;
		 case 2:
	         if(!p.hasPermission("man10.siki.delete")){
	                p.sendMessage(prefix + "あなたに○○式をclearする権限はありません！");
	                return true;
	            }
			 if(args[0].equalsIgnoreCase("clear")) {
				 if(config1.getString("siki."+args[1].toString()) == null) {
					 sender.sendMessage(prefix+"§4"+"その○○式はありません！");
					 return true;
				 } 
				 config1.set(config1.getString("siki"+args[1].toString()), null);
				 sender.sendMessage(prefix+"§4"+args[1].toString()+"を削除しました。");
			 }
			 break;
		 case 3:
		  try {
			  Integer.parseInt(args[0]);
			    try {
			        Integer.parseInt(args[1]);
			        double i1 = Double.parseDouble(args[1]);
			        double i2 = Double.parseDouble(args[0]);
			        for (String key : config1.getConfigurationSection("siki").getKeys(false)) {
			            if(config1.getString("siki." + key + ".player") == sender.getName().toString()){
							 sender.sendMessage(prefix+"§4"+"すでに○○式を登録しています！");
							 return true;
			            }
			        }
			        if(i2 <= 1) {
						 sender.sendMessage(prefix+"§4"+"式の数は2以上にしてください！");
						 return true;
					 } 
			        if(i2 <= 1) {
						 sender.sendMessage(prefix+"§4"+"式の数は2以上にしてください！");
						 return true;
					 } 
			        if(i1 <= 9999) {
						 sender.sendMessage(prefix+"§4"+"かける金額は$10000以上にしてください！");
						 return true;
					 } 
			        if(config1.getString("siki."+args[2].toString()) != null) {
						 sender.sendMessage(prefix+"§4"+"その○○式は存在するため別の名前にしてください！");
						 return true;
					 }
			        config1.set("siki."+args[2].toString()+".player",p.getName().toString());
					config1.set("siki."+args[2].toString()+".money",args[1]);
					config1.set("siki."+args[2].toString()+".Osiki",args[0]);
					saveConfig();
					if(Vault.economy.getBalance(p)>=i1) {
						Bukkit.broadcastMessage(prefix+"§r§l"+p.getName().toString()+"が$"+args[1]+"で"+args[0]+"式を始めました！参加=>/ms "+args[2].toString());
						saveConfig();
						BukkitScheduler scheduler = getServer().getScheduler();
						scheduler.scheduleSyncDelayedTask(pl, new Runnable() {

							@Override
							public void run() {
								if(config1.getString("siki."+args[2].toString()) != null) {
									 config1.set("siki."+args[2].toString(), null);
									 p.sendMessage(prefix+"5分立っても挑戦者が現れないためキャンセルします。");
								}
							}
						}, 6000);
				        return true;
			        }else {
			        	sender.sendMessage(prefix+"お金を持っていません。");
						 config1.set("siki."+args[2].toString(), null);
						 saveConfig();
			        }
			    } catch (NumberFormatException e) {
					sender.sendMessage(prefix+"数字を入力してね。=>/ms 100 10000  テスト");
					return true;
			    }
			    } catch (NumberFormatException e) {
					  sender.sendMessage(prefix+"数字を入力してね。=>/ms 100 10000  テスト");
					  return true;
			    }
		  default:
			  sender.sendMessage(prefix+"/ms やりたい式の数字 掛け金 ○○式につける名前");
			  sender.sendMessage(prefix+"参加=> /ms 参加したい○○式の名前");
			  break;
		}
		return true;
		}

	@Override
	public void onDisable() {
		super.onDisable();
		saveConfig();
		reloadConfig();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		getLogger().info("===============Man10〇〇式===============");
		getLogger().info("         ver1.0.0 作成者:Mr_IK");
		getLogger().info("======================================");
		getCommand("ms").setExecutor(this);
	    saveDefaultConfig();
	    FileConfiguration config = getConfig();
        config1 = config;
		config1.set("siki", null);
		new Vault(this);
    }

}
