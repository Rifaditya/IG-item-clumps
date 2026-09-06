<p align="center">
  <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&amp;logo=fabric" alt="Requires Fabric API"></a>
  <a href="https://www.curseforge.com/minecraft/mc-mods/dasik-libary"><img src="https://img.shields.io/badge/Requires-Dasik_Library-8A2BE2?style=for-the-badge" alt="Requires Dasik Library"></a>
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&amp;logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

<h2>📦 Item Clumps</h2>

<blockquote><p><strong>&ldquo;Stop Ground Item Lag. Merge Endless Drops into Ultra-Smooth Clumps.&rdquo;</strong></p></blockquote>

<blockquote><p><strong>1 Jar 1 Version Policy:</strong> I build <strong>1 dedicated JAR for each Minecraft version</strong> (e.g. MC 26.2, MC 26.3). Please download the exact build that matches your Minecraft installation.<br><br><strong>Dependency Requirement:</strong> For modern Minecraft 26.x releases (26.1.2, 26.2, 26.3+), this mod requires both <strong>Fabric API</strong> and <strong>Dasik Library</strong> (<code>v1.7.4+</code>).</p></blockquote>

<p>In vanilla Minecraft, dropped items merge, but they strictly cap out at a standard stack size of 64. When an automated mob farm, quarry, tree harvester, or massive TNT explosion spills thousands of items onto the ground, your game is forced to tick, track, and render dozens&mdash;or even hundreds&mdash;of individual 3D item entities. The result? Severe client-side frame stuttering, rendering bottlenecks, and catastrophic server TPS drops.</p>

<p><strong>Item Clumps</strong> shatters the 64-item ground entity barrier server-side. It aggressively merges identical dropped items into single, ultra-lightweight virtual mega-stacks of up to <strong>9,999 items by default</strong> (or up to <strong>2,147,483,647</strong>). Floating holographic count labels render cleanly above clumps, hoppers drip-feed items smoothly without jamming, and vanilla clients can connect to servers without installing any client mod!</p>

<p>Part of the <strong>Instant Gratification Collection</strong> &mdash; mods that respect the player's time.</p>

<hr>

<p align="center">
  <strong>🎬 Video Showcase: Item Clumps in Action</strong><br>
  <em>Click the thumbnail or button below to watch the live demonstration on YouTube</em><br><br>
  <a href="https://youtu.be/2e9tHTHidfo" target="_blank" rel="noopener">
    <iframe src="https://www.youtube.com/embed/2e9tHTHidfo?feature=youtu" width="560" height="314" allowfullscreen="allowfullscreen"></iframe>
  </a><br><br>
  <a href="https://youtu.be/2e9tHTHidfo" target="_blank" rel="noopener">
    <img src="https://img.shields.io/badge/▶️_Watch_Video-Play_on_YouTube-FF0000?style=for-the-badge&amp;logo=youtube&amp;logoColor=white" alt="▶️ Play Video on YouTube">
  </a>
</p>

<hr>

<h2>✨ Features</h2>

<h3>🚀 Server-Side Stack Aggregation &amp; Virtualization</h3>
<p>Bypasses the vanilla 64-item ground entity ceiling entirely. Identical dropped items (sharing matching item types, DataComponents, and targeting) merge into single unified entities containing up to <strong>9,999 items</strong> by default, fully configurable up to <strong>2,147,483,647</strong> (<code>Integer.MAX_VALUE</code>).</p>

<blockquote><p><strong>💡 Virtual Count Storage:</strong> The full virtual item count is stored directly in the standard vanilla <code>ItemStack</code> count. Because modern Minecraft serializes stack counts using dynamic VarInts rather than legacy bytes, vanilla clients natively receive, synchronize, and display these counts on ground entities.</p></blockquote>

<h3>🏷️ Dynamic Holographic Count Labels</h3>
<p>Renders a custom floating nameplate (e.g. <code>Cobblestone x1450</code> or <code>Rotten Flesh x420</code>) above clumps whenever the entity count exceeds a normal stack. Uses Minecraft's native entity nametag rendering (<code>setCustomName</code> and <code>setCustomNameVisible</code>) on the server so that vanilla clients see the label without needing any client-side mod installed.</p>

<h3>⏱️ Vanilla Despawn Timer Preservation</h3>
<p>To protect your hard-earned resources, merged clumps inherit the age of the <strong>youngest</strong> constituent item in the merge (<code>Math.min(this.age, other.age)</code>). This ensures combining newly dropped items with older items never causes premature despawning.</p>

<h3>📦 Smart Inventory Transfer &amp; Chunked Pickups</h3>
<p>Walking over a massive clump never wastes or deletes items. The mod calculates your player inventory's free capacity and transfers only what you can hold in full stack chunks, leaving the remaining items safely on the ground in a smaller clump with immediate visual count updates.</p>

<h3>⚙️ Hopper Drip-Feed Protection</h3>
<p>Hoppers under an Item Clump extract exactly 1 item per normal vanilla transfer cycle via <code>HopperBlockEntityMixin</code>, decrementing the clump smoothly. Your sorting systems, storage silos, and redstone item elevators continue to function at standard vanilla transfer cadences without stalling or swallowing full mega-stacks.</p>

<h3>📐 Horizontal Search Radius &amp; Vertical Layer Guard</h3>
<p>Merges identical items within a configurable horizontal radius (<code>item_clumps:merge_radius</code>, default: <code>1</code> block). Crucially, the merging algorithm enforces a vertical boundary: items resting 1 block above or below will not merge through ceilings, floors, or platforms.</p>

<h3>🧲 Companion Mod Synergy (Stack Size Adjuster &amp; Magnet)</h3>
<ul>
  <li><strong>Stack Size Adjuster Integration:</strong> When <strong>Stack Size Adjuster</strong> is present, Item Clumps automatically defers ground entity caps to your custom configured stack sizes, cleanly hiding the redundant <code>max_clump_size</code> GameRule from the settings menu.</li>
  <li><strong>Magnet Flight Compatibility:</strong> Built-in reflection hooks detect items actively traveling through the air towards players via <strong>Magnet</strong>, temporarily exempting them from clumping until flight completes for smooth, fluid motion trajectories.</li>
</ul>

<h3>🧩 Compatibility &amp; HUD Integration</h3>
<ul>
  <li><strong>100% Server-Side Compatible:</strong> Runs entirely on the server. Vanilla clients can join modded dedicated servers without downloading anything.</li>
  <li><strong>YetAnotherConfigLib (YACL) &amp; ModMenu:</strong> Optional graphical configuration screen in singleplayer to easily customize world defaults.</li>
  <li><strong>Zero NBT Pollution:</strong> In-memory damage and entity interception guarantees your world saves remain 100% vanilla safe.</li>
</ul>

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/IG-item-clumps/main/Doc/Media/Gamerule%20Screen.png" alt="Item Clumps Native Game Rules Menu" width="85%"><br>
  <em>Native in-game Edit Game Rules menu showing the dedicated Item Clumps category</em>
</p>

<hr>

<h2>📊 Quick Reference &amp; Mechanics Matrix</h2>

<table>
  <thead>
    <tr>
      <th>Gameplay Aspect</th>
      <th>Vanilla Minecraft</th>
      <th>Item Clumps (Modern 26.2+)</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Max Ground Stack Size</strong></td>
      <td>64 items (16 for pearls/eggs, 1 for tools)</td>
      <td><strong>9,999 items</strong> (Configurable up to 2.14 Billion)</td>
    </tr>
    <tr>
      <td><strong>Entity Count for 5,000 Items</strong></td>
      <td>79 separate 3D entity models</td>
      <td><strong>1 single unified entity</strong></td>
    </tr>
    <tr>
      <td><strong>Frame Rate &amp; Tick Impact</strong></td>
      <td>Severe stuttering, FPS drops, TPS decay</td>
      <td><strong>Smooth 60 FPS</strong>, zero entity tick lag</td>
    </tr>
    <tr>
      <td><strong>Holographic Count Label</strong></td>
      <td>❌ None (Indistinguishable pile)</td>
      <td>✅ <strong>Live 3D nametag</strong> (<code>Item Name xCount</code>)</td>
    </tr>
    <tr>
      <td><strong>Despawn Timer Logic</strong></td>
      <td>Merges reset to oldest item age</td>
      <td>✅ <strong>Inherits youngest age</strong> (Maximum lifespan)</td>
    </tr>
    <tr>
      <td><strong>Hopper Extraction</strong></td>
      <td>1 item pulled per cycle</td>
      <td>✅ <strong>Safe 1-item drip feed</strong> (No redstone breakage)</td>
    </tr>
    <tr>
      <td><strong>Magnet Flight Behavior</strong></td>
      <td>Glitchy mid-air merging</td>
      <td>✅ <strong>Aerodynamic flight lock</strong> (No sudden snapping)</td>
    </tr>
    <tr>
      <td><strong>Client Requirement</strong></td>
      <td>Required on client</td>
      <td>✅ <strong>100% Server-Side</strong> (Vanilla clients supported)</td>
    </tr>
  </tbody>
</table>

<hr>

<h2>🚀 In-Game Commands &amp; Quick Start</h2>

<p>Adjust ground clumping rules live in your active world without restarting using standard Minecraft <code>/gamerule</code> commands:</p>

<pre><code>/gamerule item_clumps:enable_clumping true
/gamerule item_clumps:max_clump_size 9999
/gamerule item_clumps:render_labels true
/gamerule item_clumps:label_min_count -1
/gamerule item_clumps:merge_radius 1</code></pre>

<p>All modifications made via <code>/gamerule</code> take effect immediately and synchronize across all connected players.</p>

<hr>

<h2>⚙️ Configuration (Native GameRules)</h2>

<blockquote><p><strong>💡 Config vs. In-Game GameRules:</strong> The global configuration file (<code>config/item_clumps.json</code>) only defines default values for newly created worlds. In existing worlds, change settings in-game via the <strong>Edit Game Rules</strong> UI screen or the <code>/gamerule</code> command.</p></blockquote>

<table>
  <thead>
    <tr>
      <th>GameRule Name</th>
      <th>Type</th>
      <th>Default</th>
      <th>Valid Range</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><code>item_clumps:enable_clumping</code></td>
      <td>Boolean</td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Toggles the item clumping mechanic on or off. When disabled, vanilla merging applies.</td>
    </tr>
    <tr>
      <td><code>item_clumps:max_clump_size</code></td>
      <td>Integer</td>
      <td><code>9999</code></td>
      <td><code>64</code> to <code>2,147,483,647</code></td>
      <td>Maximum number of items a single clump can hold. <em>(Hidden when Stack Size Adjuster is loaded)</em>.</td>
    </tr>
    <tr>
      <td><code>item_clumps:render_labels</code></td>
      <td>Boolean</td>
      <td><code>true</code></td>
      <td><code>true / false</code></td>
      <td>Renders a 3D holographic count label above clumps larger than a normal stack.</td>
    </tr>
    <tr>
      <td><code>item_clumps:label_min_count</code></td>
      <td>Integer</td>
      <td><code>-1</code></td>
      <td><code>-1</code> to <code>2,147,483,647</code></td>
      <td>Minimum count required before displaying holographic label. Set to <code>-1</code> to use vanilla max stack limit.</td>
    </tr>
    <tr>
      <td><code>item_clumps:merge_radius</code></td>
      <td>Integer</td>
      <td><code>1</code></td>
      <td><code>1</code> to <code>10</code></td>
      <td>Horizontal block radius items will search to find and merge with identical ground items.</td>
    </tr>
  </tbody>
</table>

<hr>

<h2>📖 In-Depth How-To &amp; Operational Playbook</h2>

<h3>1. Drop-In Setup &amp; Baseline Initialization</h3>
<ol>
  <li>Place <code>item-clumps-*.jar</code> along with <strong>Fabric API</strong> and <strong>Dasik Library</strong> into your <code>mods</code> folder.</li>
  <li>Launch Minecraft. The mod will automatically generate <code>config/item_clumps.json</code> populated with recommended high-performance defaults.</li>
</ol>

<h3>2. Live In-Game Tuning vs. Global Template</h3>
<ul>
  <li><strong>For New Worlds:</strong> Configure your desired settings in <code>config/item_clumps.json</code> or via ModMenu + YACL on the title screen. New worlds copy these values into world GameRules upon creation.</li>
  <li><strong>For Existing Worlds:</strong> Open the pause menu &rarr; <strong>Edit Game Rules</strong> &rarr; scroll to the <strong>Item Clumps</strong> section, or use <code>/gamerule item_clumps:&lang;rule&rang; &lang;value&rang;</code> in chat.</li>
</ul>

<h3>3. Industrial Farm Optimization (Mob Farms &amp; Quarries)</h3>
<ul>
  <li>For high-volume automated mob grinders or quarry tunnels, set <code>item_clumps:merge_radius 2</code> or <code>3</code>. This allows items dropped across multiple hopper channels or water streams to cluster into a single central entity.</li>
  <li>If you notice holographic labels causing visual clutter in farm collection pits, raise <code>item_clumps:label_min_count</code> to <code>256</code> or <code>1000</code> so only massive surplus clumps display floating text.</li>
</ul>

<h3>4. Storage &amp; Hopper Sorting Safeguards</h3>
<ul>
  <li>Item Clumps does not require custom collection filters. Standard hopper sorters and item elevators will ingest items smoothly 1 by 1.</li>
  <li>Because clumps shrink progressively as hoppers pull from them, the floating label updates in real time to show the remaining quantity.</li>
</ul>

<h3>5. Troubleshooting &amp; Crash Prevention</h3>
<ul>
  <li><strong>Classloader Guard:</strong> Item Clumps contains Knot ClassLoader startup checks to ensure compatibility across Minecraft versions.</li>
  <li><strong>Overflow Prevention:</strong> Clump sum calculations utilize 64-bit arithmetic to ensure total item counts never overflow into negative values.</li>
</ul>

<hr>

<h2>🧩 Recommended Sister Mods</h2>

<p>If you enjoy <strong>Item Clumps</strong>, these companion mods from the <strong>Instant Gratification Collection</strong> plug in seamlessly:</p>

<ul>
  <li>📦 <a href="https://www.curseforge.com/minecraft/mc-mods/ig-stack-size-adjuster"><strong>Stack Size Adjuster</strong></a>: Scale inventory slot and container limits from 64 up to 2.14 Billion, perfectly aligning your storage chests with ground clump thresholds.</li>
  <li>🧲 <a href="https://www.curseforge.com/minecraft/mc-mods/instant-gratification-magnet"><strong>Magnet</strong></a>: Automatically vacuum massive item clumps and XP orbs straight into your inventory from up to 64 blocks away.</li>
  <li>⛏️ <a href="https://www.curseforge.com/minecraft/mc-mods/instant-gratification-ore-amplifier"><strong>Ore Amplifier</strong></a>: Multiply vanilla and modded ore generation in newly generated chunks to feed your high-capacity storage systems.</li>
</ul>

<p><em>Explore the full <a href="https://www.curseforge.com/members/dasikigaijin/projects"><strong>Instant Gratification Collection</strong></a> for more high-convenience enhancements.</em></p>

<hr>

<h2>☕ Support</h2>

<p>If you enjoy the <strong>Instant Gratification Collection</strong>, consider fueling future development!</p>

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&amp;logo=ko-fi&amp;logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

<blockquote><p><strong>🇮🇩 Indonesian Users:</strong> SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!</p></blockquote>

<blockquote><p><strong>💡 Dedicated Server Hosting Partner:</strong> Looking for a reliable server to play with friends? Check out <strong>BisectHosting</strong> for 1-click modpack installations, automated backups, and 24/7 dedicated customer support.</p></blockquote>

<hr>

<h2>📜 Credits &amp; Modpack Permissions</h2>

<table>
  <thead>
    <tr>
      <th>Property</th>
      <th>Information</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Creator / Author</strong></td>
      <td><strong>Dasik</strong> (Rifaditya)</td>
    </tr>
    <tr>
      <td><strong>Collection</strong></td>
      <td><a href="https://www.curseforge.com/members/dasikigaijin/projects">Instant Gratification Collection</a></td>
    </tr>
    <tr>
      <td><strong>License</strong></td>
      <td><a href="https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public License v3.0 (GPLv3)</a></td>
    </tr>
    <tr>
      <td><strong>Source Code</strong></td>
      <td><a href="https://github.com/Rifaditya/IG-item-clumps">GitHub - Rifaditya/IG-item-clumps</a></td>
    </tr>
    <tr>
      <td><strong>Issue Tracker</strong></td>
      <td><a href="https://github.com/Rifaditya/IG-item-clumps/issues">GitHub Issues</a></td>
    </tr>
    <tr>
      <td><strong>Documentation / Wiki</strong></td>
      <td><a href="https://github.com/Rifaditya/IG-item-clumps/wiki">GitHub Wiki</a></td>
    </tr>
  </tbody>
</table>

<blockquote>
  <p><strong>📦 Modpack Permissions &amp; Distribution:</strong><br>
  You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (<strong>CurseForge</strong> or <strong>Modrinth</strong>). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.</p>
  <p><strong>⚖️ License &amp; Fork Guidelines (No Zero-Change Re-uploads):</strong><br>
  This project is open-source under the <strong>GNU GPLv3</strong>. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports&mdash;provided your project remains open-source under GPLv3 with proper attribution.<br>
  <strong>However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.</strong></p>
</blockquote>

<hr>

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Instant Gratification Collection</em>
</p>
