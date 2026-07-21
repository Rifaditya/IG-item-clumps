<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

<h1 align="center">📦 Item Clumps (Server Only): The "Instant Gratification" Update</h1>

<p><strong>No Backports:</strong> I will <strong>NOT</strong> backport this mod to older Minecraft versions (1.21, 1.20, etc.). Please do not ask.</p>

<p>In vanilla Minecraft, dropped items merge, but they strictly cap at a maximum stack size of 64. If a high-efficiency mob farm, automated quarry, or massive TNT explosion drops thousands of items, the game is forced to tick and render hundreds of individual ground entities. This leads to heavy client-side frame drops, rendering lag, and severe server TPS decay.</p>

<p><strong>Item Clumps (Server Only)</strong> changes this foundation by breaking the 64-item stack limit for ground entities entirely server-side. It aggressively condenses identical items into single, lightweight virtual mega-stacks of up to 9,999 items by default. Vanilla clients can connect to servers running this mod without having to install it!</p>

<hr>

<h2>🎥 Showcase Video</h2>

<p align="center">
    <a href="https://youtu.be/2e9tHTHidfo"><img src="https://img.youtube.com/vi/2e9tHTHidfo/maxresdefault.jpg" alt="Item Clumps Showcase Video" width="560"></a>
</p>

<p align="center"><em>Click the image above to watch the mod showcase in action! (CurseForge does not support embeds)</em></p>

<hr>

<h2>✨ Features</h2>

<h3>🚀 Server-Side Stack Size Virtualization</h3>
<p>Bypasses the 64-item ground stack cap. Identical items merge into single entities containing up to <strong>9,999 items</strong> by default (configurable up the full 32-bit integer limit of <strong>2,147,483,647</strong> both in the GameRules screen and via commands).</p>

<blockquote class="note">
<strong>MegaCount Strategy:</strong> In this server-side only version, the true virtual count is stored directly in the standard vanilla <code>ItemStack</code> count. Because Minecraft 26.x serializes stack counts using standard VarInts/integers rather than bytes, vanilla clients natively support, sync, and display these larger counts on item entities.
</blockquote>

<h3>🏷️ Vanilla-Compatible Count Tags</h3>
<p>Renders a custom name tag (e.g., <code>Oak Log x450</code>) above clumps when the count exceeds a normal stack. Uses vanilla's native custom name tags (<code>setCustomName</code> / <code>setCustomNameVisible</code>) on the server so that vanilla clients display the count tag without needing any client-side mod.</p>

<h3>⏱️ Vanilla Despawn Timer Rules</h3>
<p>To match vanilla merging logic, when two stacks merge, the resulting clump inherits the age of the <strong>youngest</strong> item in the merge (taking the smaller age value, as age ticks upwards). This extends/resets the despawn window for the combined clump exactly like vanilla, ensuring you don't lose items prematurely.</p>

<h3>📦 Smart Inventory Integration</h3>
<p>Walking over a clump smoothly transfers items to your inventory in max stack chunks. The mod dynamically calculates the exact amount of free space in your inventory and takes only what fits, keeping the remainder safely in the ground clump instead of deleting or de-syncing them.</p>

<h3>⚙️ Hopper Drip-Feeding</h3>
<p>Hoppers extract items from virtual clumps one by one at standard vanilla transfer speeds. This ensures your redstone automation, sorting systems, and item-elevator pipelines function exactly as they did in vanilla, keeping the game balanced.</p>

<h3>📐 Vanilla Physics Alignment</h3>
<p>To match vanilla merging rules and prevent glitches, clumping operates on a configurable horizontal block radius, but respects vertical space. Items will <strong>not</strong> merge if they are separated vertically by 1 block or more (e.g., items resting on top of a block won't merge with items below it).</p>

<hr>

<h2>⚙️ Config</h2>

<blockquote class="warning">
<strong>⚠️ Important: Config vs. In-Game GameRules</strong><br>
The global configuration file only defines <strong>default values for new worlds</strong> at creation time.
If you have <strong>already created/opened a world</strong>, changing the config file will have no effect. You must change the settings in-game using the <strong>Edit Game Rules</strong> UI screen or the <code>/gamerule</code> command.
</blockquote>

<p>The mod works out of the box with zero setup. All parameters are managed in-game using the <strong>Native Minecraft Game Rules</strong> system.</p>

<ul>
  <li><strong>In-Game:</strong> Use <code>/gamerule item_clumps:</code> for core settings.
    <ul>
      <li><code>item_clumps:enable_clumping</code> (Default: <code>true</code>) - Toggles the clumping mechanic. When disabled, items behave exactly like vanilla.</li>
      <li><code>item_clumps:max_clump_size</code> (Default: <code>9999</code>) - The maximum quantity of items a single clumped entity can contain. (Disabled automatically if Stack Size Adjuster is loaded).</li>
      <li><code>item_clumps:render_labels</code> (Default: <code>true</code>) - Toggles the rendering of the floating custom count tag above virtual clumps.</li>
      <li><code>item_clumps:merge_radius</code> (Default: <code>1</code>) - The horizontal block radius items will search to find matching items to merge.</li>
      <li><code>item_clumps:label_min_count</code> (Default: <code>-1</code>) - Minimum item count before the clump label displays. Set to <code>-1</code> to use the default vanilla stack limit.</li>
    </ul>
  </li>
</ul>

<p align="center">
    <img src="https://raw.githubusercontent.com/Rifaditya/IG-item-clumps/main/Doc/Media/Gamerule%20Screen.png" alt="Item Clumps Game Rules Screenshot">
</p>

<blockquote class="info">
<strong>Recommended Mod:</strong> Since this mod adds several GameRules, we highly recommend using <a href="https://www.curseforge.com/minecraft/mc-mods/collapsible-gamerules"><strong>Collapsible Game Rules</strong></a> to keep your settings menu clean and organized.
</blockquote>

<hr>

<h2>📦 Installation & Environment</h2>

<h3>🖥️ Environment Support</h3>
<ul>
  <li><strong>Server-side only:</strong> All functionality is done server-side and is compatible with vanilla clients.
    <ul>
      <li>Works in singleplayer too</li>
    </ul>
  </li>
</ul>

<h3>📥 Install Instructions</h3>
<ol>
  <li>Install <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><strong>Fabric API</strong></a>.</li>
  <li>Download <code>item-clumps-*.jar</code> and place it in your <code>mods</code> folder.</li>
</ol>

<hr>

<h2>🧩 Compatibility</h2>

<table border="1" cellpadding="5" cellspacing="0">
  <thead>
    <tr>
      <th>Feature</th>
      <th align="center">Fabric (26.2+)</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Singleplayer</td>
      <td align="center">✅</td>
    </tr>
    <tr>
      <td>Multiplayer (LAN/Server)</td>
      <td align="center">✅</td>
    </tr>
    <tr>
      <td>Empty Dimensions</td>
      <td align="center">✅</td>
    </tr>
  </tbody>
</table>

<h3>🎮 Version Compatibility & Support</h3>
<ul>
  <li><strong>Minecraft 26.2+:</strong> Current public release — Active & Supported.</li>
  <li><strong>Minecraft 26.1.2:</strong> Discontinued</li>
</ul>

<hr>

<h2>☕ Support</h2>

<p>If you enjoy the <strong>Instant Gratification</strong> collection, consider fueling the next update!</p>

<p align="center">
    <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
    <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
    <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

<hr>

<h2>📜 Credits</h2>

<table border="1" cellpadding="5" cellspacing="0">
  <thead>
    <tr>
      <th>Role</th>
      <th>Author</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Creator</strong></td>
      <td>Rifaditya (Dasik)</td>
    </tr>
    <tr>
      <td><strong>Collection</strong></td>
      <td>Instant Gratification</td>
    </tr>
    <tr>
      <td><strong>License</strong></td>
      <td>GNU GPLv3</td>
    </tr>
  </tbody>
</table>

<br>

<blockquote>
    <strong>📦 Modpack Permissions & Distribution:</strong><br>
    You are free to include this mod in any modpack on any platform. However, the mod itself must be downloaded from its official distribution pages on <strong>Modrinth</strong> or <strong>CurseForge</strong>. Re-uploading or redistributing the mod jar file to third-party sites is strictly prohibited unless explicitly permitted by the creator.
    <br><br>
    <strong>License & Forks:</strong><br>
    Since the source code is licensed under <strong>GNU GPLv3</strong>, you are fully permitted to fork the repository, make modifications, build your own versions, and distribute them under the terms of the GPLv3. The prohibition on third-party redistribution applies exclusively to the official compiled releases/jars published by the original creator (Dasik/Rifaditya). Forks must be published as distinct projects, not direct re-uploads of official builds.
</blockquote>


<hr>

<div align="center">
    <p><strong>Made with ❤️ for the Minecraft community</strong></p>
    <p><em>Part of the Instant Gratification Collection</em></p>
</div>
