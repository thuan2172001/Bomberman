# Bài tập lớn OOP - Bomberman Game của nhóm GGAA
## Trưởng nhóm : Trịnh Văn Thuận <br/> Thành viên : Đinh Thanh Nhàn & Vũ Đức Giang

### Mô tả về các đối tượng trong trò chơi
Nếu bạn đã từng chơi Bomberman, bạn sẽ cảm thấy quen thuộc với những đối tượng này. Chúng được được chia làm hai loại chính là nhóm đối tượng động (*Bomber*, *Enemy*, *Bomb*) và nhóm đối tượng tĩnh (*Grass*, *Wall*, *Brick*, *Door*, *Item*).

- ![](res/textures/p1.png) *Bomber* là nhân vật chính của trò chơi. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi. 
- ![](res/textures/dragonDefault.jpg) *Enemy* là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua Level. Enemy có thể di chuyển ngẫu nhiên hoặc tự đuổi theo Bomber tùy theo loại Enemy. Các loại Enemy sẽ được mô tả cụ thể ở phần dưới.
- *Bomb* là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô Grass. Khi đã được kích hoạt, Bomber và Enemy không thể di chuyển vào vị trí Bomb. Tuy nhiên ngay khi Bomber vừa đặt và kích hoạt Bomb tại ví trí của mình, Bomber có một lần được đi từ vị trí đặt Bomb ra vị trí bên cạnh. Sau khi kích hoạt 2s, Bomb sẽ tự nổ, các đối tượng *Flame* được tạo ra.


- *Grass* ![](res/textures/grass.png), *Snow* ![](res/textures/snowtile.png), *Biscuit* ![](res/textures/biscuit1.png), *Tile* ![](res/textures/blocktile.jpg), *Cloud* ![](res/textures/palacetile.png) là đối tượng mà Bomber và Enemy có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó
- *Wall* ![](res/textures/hardWall.png), *Water* ![](res/textures/water.png), *Lava* ![](res/textures/lava.png), *Pizza* ![](res/textures/pizza1.png), *Candy* ![](res/textures/candy1.png), *Tree* ![](res/textures/tree.png) ![](res/textures/snowTree.png) là đối tượng cố định, không thể phá hủy bằng Bomb cũng như không thể đặt Bomb lên được, Bomber và Enemy không thể di chuyển vào đối tượng này
- *Box* ![](res/textures/box1.png) ![](res/textures/box2.png) ![](res/textures/box3.png) ![](res/textures/box4.png) ![](res/textures/box5.png) ![](res/textures/box6.png) ![](res/textures/box7.png) ![](res/textures/box8.png) ![](res/textures/food0.png) ![](res/textures/food1.png) ![](res/textures/food2.png) ![](res/textures/food3.png) là đối tượng được đặt lên các ô Grass, không cho phép đặt Bomb lên nhưng có thể bị phá hủy bởi Bomb được đặt gần đó. Bomber và Enemy thông thường không thể di chuyển vào vị trí Brick khi nó chưa bị phá hủy.


- ![](res/textures/portal.png) *Portal* là đối tượng được giấu phía sau một đối tượng Brick. Khi Brick đó bị phá hủy, Portal sẽ hiện ra ngẫu nhiên với tỉ lệ thấp
Bạn sẽ qua màn nếu bạn đi qua portal hoặc nếu tất cả Enemy đã bị tiêu diệt

Các *Item* cũng được giấu phía sau Brick và chỉ hiện ra khi Brick bị phá hủy. Bomber có thể sử dụng Item bằng cách di chuyển vào vị trí của Item. Thông tin về chức năng của các Item được liệt kê như dưới đây:
- ![](res/textures/power_speed.png) *SpeedItem* Khi sử dụng Item này, Bomber sẽ được tăng vận tốc di chuyển thêm một giá trị thích hợp
- ![](res/textures/power_fireup.png) *FlameItem* Item này giúp tăng phạm vi ảnh hưởng của Bomb khi nổ (độ dài các Flame lớn hơn)
- ![](res/textures/power_bomb.png) *BombItem* Thông thường, nếu không có đối tượng Bomb nào đang trong trạng thái kích hoạt, Bomber sẽ được đặt và kích hoạt duy nhất một đối tượng Bomb. Item này giúp tăng số lượng Bomb có thể đặt thêm một.
- ![](res/textures/power_kick.png) *KickItem* Giúp Bomber có khả năng đá bomb đi xa
- ![](res/textures/power_pierce.png) *PiereItem* Tăng khẳ năng đâm xuyên của bomb, xuyên các vật thể mềm (BOX)
- ![](res/textures/power_timer.png) *TimeItem* Giảm thời gian bomb nổ

Có nhiều loại Enemy trong Bomberman, tuy nhiên trong phiên bản này chỉ yêu cầu cài đặt hai loại Enemy dưới đây (nếu cài đặt thêm các loại khác sẽ được cộng thêm điểm):
- ![](res/textures/dragonDefault.jpg) *Dragon* là Enemy đơn giản nhất, di chuyển với vận tốc cố định bằng vận tốc bomber, có thể phá item trên đường đi
- ![](res/textures/fireMonsterDefault.png) *FireMonster* là Enemy mạnh, có tốc độ di chuyển nhanh, có khả năng ăn item để tăng tốc độ của mình
- ![](res/textures/dragonRiderDefault.png) *DragonRider* là Enemy có sức mạnh khủng khiếp, tốc độ di chuyển nhanh và có khả năng đi SĂN bomber

## Mô tả game play, xử lý va chạm và xử lý bom nổ
- Trong một màn chơi, Bomber sẽ được người chơi di chuyển, đặt và kích hoạt Bomb với mục tiêu chính là tiêu diệt tất cả Enemy hoặc tìm ra Portal để có thể qua màn mới
- Bomber sẽ bị giết khi va chạm với Enemy hoặc thuộc phạm vi Bomb nổ. Lúc đấy trò chơi kết thúc.
- Enemy bị tiêu diệt khi thuộc phạm vi Bomb nổ
- Một đối tượng thuộc phạm vi Bomb nổ có nghĩa là đối tượng đó va chạm với một trong các tia lửa được tạo ra tại thời điểm một đối tượng Bomb nổ.

- Khi Bomb nổ, một Flame trung tâm tại vị trí Bomb nổ và bốn Flame tại bốn vị trí ô đơn vị xung quanh vị trí của Bomb xuất hiện theo bốn hướng trên/dưới/trái/phải. Độ dài bốn Flame xung quanh mặc định là 1 đơn vị, được tăng lên khi Bomber sử dụng các FlameItem.
- Khi các Flame xuất hiện, nếu có một đối tượng thuộc loại Box/Wall nằm trên vị trí một trong các Flame thì độ dài Flame đó sẽ được giảm đi để sao cho Flame chỉ xuất hiện đến vị trí đối tượng Box/Wall theo hướng xuất hiện. Lúc đó chỉ có đối tượng Box/Wall bị ảnh hưởng bởi Flame, các đối tượng tiếp theo không bị ảnh hưởng. Còn nếu vật cản Flame là một đối tượng Bomb khác thì đối tượng Bomb đó cũng sẽ nổ ngay lập tức.
- Trong trường hợp bomb của bạn có khả năng đâm xuyên cực mạnh (PierceItem) thì nó sẽ đâm thủng qua các Box nhưng vẫn không thể xuyên qua Wall
- Có tổng cộng 10 màn chơi, nếu vượt qua màn 10, bạn sẽ chiến thắng tựa game này