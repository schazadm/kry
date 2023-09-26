modgroup(m) =
{
print1("{");

for (i=1,m-1,
	if (gcd(i,m)==1,
	print1(i);
	if(i!=m-1,print1(", "));
	);
);

print1("}");
}